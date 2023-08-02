package com.android.slachat.ui.chatlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.slachat.R
import com.android.slachat.data.ChatItemModel
import com.android.slachat.repository.ChatListRepository
import org.koin.androidx.compose.get

@Composable
fun ChatScreen() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        ItemsList()

        Box(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = {
                },
                containerColor = colorResource(id = R.color.login_button)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun ItemsList() {
    val chatListRepository = get<ChatListRepository>()
    LazyColumn {
        items(chatListRepository.getList()) {
            ChatItem(model = it)
        }
    }
}

@Composable
fun CircleImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .size(60.dp)
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

@Composable
fun ChatItem(model: ChatItemModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_small))
        ) {

            CircleImage(imageUrl = model.imageUrl)
            Spacer(modifier = Modifier.width(15.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                    text = model.username,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = model.lastMessage,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = model.messageTime,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }

        Divider(
            color = colorResource(id = R.color.silver),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .align(Alignment.BottomCenter)
                .offset(x = 85.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        ChatScreen()
    }
}
