package com.android.slachat.ui.conversation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.android.slachat.R
import com.android.slachat.data.ConversationUserModel
import com.android.slachat.ui.conversation.model.ConversationModel
import com.android.slachat.viewmodel.ConversationViewModel
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(navController: NavController, userId: String?) {
    val viewModel = get<ConversationViewModel>()
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    val messages = remember { mutableStateListOf<ConversationModel>() }.also { list ->
        list.addAll(viewModel.getParsedData())
    }

    val disposableHandle = remember { mutableStateOf<DisposableHandle?>(null) }

    DisposableEffect(Unit) {
        val handle = scope.launch {
            viewModel.newMessageEvent.collect { newMessage ->
                val updatedMessage = ConversationModel(
                    newMessage,
                    viewModel.getCurrentTime(),
                    newMessage
                )
                messages.add(updatedMessage)
            }
        }
        disposableHandle.value = handle.invokeOnCompletion { }

        onDispose {
            handle.cancel()
        }
    }


    Scaffold(
        topBar = {
            SlachatAppBar(
                title = { ToolbarTitle(conversationUserModel = viewModel.model) },
                handleBack = { navController.popBackStack() }
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ConversationMessages(
                model = viewModel.model,
                items = messages,
                modifier = Modifier.weight(1f),
                scrollState = scrollState
            )
            UserInput(
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(messages.lastIndex)
                    }
                },
                onMessageSent = { content ->
                    messages.add(ConversationModel(" ", "228", content))
                },
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            )
        }

    }
}

@Composable
fun ConversationMessages(
    model: ConversationUserModel,
    items: List<ConversationModel>,
    modifier: Modifier,
    scrollState: LazyListState,
) {
    Box(modifier = modifier) {
        LazyColumn(
            state = scrollState,
        ) {
            items(items) { it ->
                if (it.userName.isBlank()) MyMessage(conversationUserModel = model, it)
                else Message(conversationUserModel = model, message = it)
            }
        }
    }
}

@Composable
fun Message(conversationUserModel: ConversationUserModel, message: ConversationModel) {
    Row(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_view))) {
        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(42.dp)
                .border(1.5.dp, colorResource(id = R.color.light_silver), CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                .clip(CircleShape)
                .align(Alignment.Top),
            painter = rememberAsyncImagePainter(model = conversationUserModel.image),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        AuthorAndTextMessage(messageModel = message, isUserMe = false)
    }
}

@Composable
fun MyMessage(conversationUserModel: ConversationUserModel, message: ConversationModel) {
    Row(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_view))) {
        Spacer(modifier = Modifier.weight(1f))
        AuthorAndTextMessage(messageModel = message, isUserMe = true)
        Image(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(42.dp)
                .border(1.5.dp, colorResource(id = R.color.light_silver), CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                .clip(CircleShape)
                .align(Alignment.Top),
            painter = rememberAsyncImagePainter(model = conversationUserModel.image),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
    }
}

@Composable
fun AuthorAndTextMessage(
    messageModel: ConversationModel,
    modifier: Modifier = Modifier,
    isUserMe: Boolean
) {
    Column(modifier) {
        if (!isUserMe) AuthorNameTimestamp(messageModel = messageModel)
        Spacer(modifier = Modifier.height(4.dp))
        ChatItemBubble(message = messageModel, isUserMe = isUserMe)
    }
}


@Composable
private fun AuthorNameTimestamp(messageModel: ConversationModel) {
    Row(
        modifier = Modifier
            .semantics(mergeDescendants = true) {}) {
        Text(
            text = messageModel.userName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp) // Space to 1st bubble
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = messageModel.time,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignBy(LastBaseline),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ChatItemBubble(
    message: ConversationModel,
    isUserMe: Boolean
) {
    val backgroundBubbleColor = if (isUserMe) {
        colorResource(id = R.color.my_message_color)
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val shape = if (isUserMe) ChatBubbleShapeMe else ChatBubbleShapeAnotherUser
    val modifier =
        if (isUserMe) Modifier.padding(start = dimensionResource(id = R.dimen.padding_view))
        else Modifier.padding(end = dimensionResource(id = R.dimen.padding_view))

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = shape,
            modifier = modifier
        ) {
            ClickableMessage(
                message = message,
            )
        }
    }
}

@Composable
fun ClickableMessage(
    message: ConversationModel
) {
    val width = getWidthDp(LocalContext.current)
    Text(
        text = message.message,
        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
        modifier = Modifier
            .padding(16.dp)
            .widthIn(0.dp, width.dp),
    )
}

fun getWidthDp(context: Context): Float {
    val displayMetrics = context.resources.displayMetrics
    return (displayMetrics.widthPixels / displayMetrics.density) * 0.65f
}


private val ChatBubbleShapeAnotherUser = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
private val ChatBubbleShapeMe = RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        val navController = rememberNavController()
        ConversationScreen(navController = navController, userId = "1")
    }
}

