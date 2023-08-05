package com.android.slachat.ui.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
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
import org.koin.androidx.compose.get


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationScreen(navController: NavController) {
    val viewModel = get<ConversationViewModel>()
    viewModel.getParsedData()

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
            ConversationMessages(model = viewModel.model, viewModel.getParsedData())
            //MyMessage(viewModel.model, viewModel.getParsedData().first())
        }

    }
}

@Composable
fun ConversationMessages(model: ConversationUserModel, items: List<ConversationModel>) {
    LazyColumn {
        items(items) {
            Message(conversationUserModel = model, it)
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
        AuthorAndTextMessage(messageModel = message)
    }
}

@Composable
fun MyMessage(conversationUserModel: ConversationUserModel, message: ConversationModel) {
    Row(modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_view))) {
        Spacer(modifier = Modifier.weight(1f))
        AuthorAndTextMessage(messageModel = message.copy(userName = ""))
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
fun AuthorAndTextMessage(messageModel: ConversationModel,modifier: Modifier= Modifier) {
    Column (modifier){
        AuthorNameTimestamp(messageModel = messageModel)
        Spacer(modifier = Modifier.height(4.dp))
        ChatItemBubble(message = messageModel, isUserMe = messageModel.userName.isBlank())
    }
}

@Composable
private fun AuthorNameTimestamp(messageModel: ConversationModel) {
    Row(modifier = Modifier.semantics(mergeDescendants = true) {}) {
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
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Column {
        Surface(
            color = backgroundBubbleColor,
            shape = ChatBubbleShapeAnotherUser,
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_view))
        ) {
            ClickableMessage(
                message = message
            )
        }
    }
}

@Composable
fun ClickableMessage(
    message: ConversationModel
) {
    Text(
        text = message.message,
        style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
        modifier = Modifier.padding(16.dp),
    )
}

private val ChatBubbleShapeAnotherUser = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)


@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        val navController = rememberNavController()
        ConversationScreen(navController = navController)
    }
}

