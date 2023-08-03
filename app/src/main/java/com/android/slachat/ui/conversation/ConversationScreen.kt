package com.android.slachat.ui.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.android.slachat.R
import com.android.slachat.data.ConversationUserModel


@Composable
fun ConversationScreen(navController: NavController) {
    val model = ConversationUserModel(
        "Kotik 228",
        true,
        image = "https://www.webbox.co.uk/wp-content/uploads/2020/10/angry_cat_2-scaled.jpg"
    )
    SlachatAppBar(
        title = { ToolbarTitle(conversationUserModel = model) },
        handleBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlachatAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    handleBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        actions = actions,
        title = title,
        navigationIcon = {
            BackButton(
                Modifier
                    .padding(dimensionResource(id = R.dimen.padding_view))
                    .clickable { handleBack.invoke() })
        }
    )
}

@Composable
fun BackButton(modifier: Modifier) {
    Icon(
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = null,
        modifier = modifier
    )
}


@Composable
fun ToolbarTitle(conversationUserModel: ConversationUserModel) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleImage(imageUrl = conversationUserModel.image)
        Spacer(modifier = Modifier.width(15.dp))
        Column(
            modifier = Modifier.run {
                width(IntrinsicSize.Max)
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.medium_padding)
                    )
            }
        ) {
            Text(
                text = conversationUserModel.userName,
                style = MaterialTheme.typography.titleMedium
            )
            val onlineStatus = if (conversationUserModel.isOnline) "Online" else {
                conversationUserModel.lastSeen.ifBlank { "Offline" }
            }
            Text(
                text = onlineStatus,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

}

@Composable
fun CircleImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        val navController = rememberNavController()
        ConversationScreen(navController = navController)
    }
}

