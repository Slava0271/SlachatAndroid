package com.android.slachat.ui.chatlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Divider
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
            contentScale = ContentScale.Crop, // Use Crop to fill the circular container without distortion
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ChatItem(model: ChatItemModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Make the Box take up the full width of the parent
            .padding(all = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            CircleImage(imageUrl = model.imageUrl)
            Spacer(modifier = Modifier.width(15.dp))

            Column(modifier = Modifier.weight(1f)) { // Added weight modifier to take up all available horizontal space
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
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        // Divider that starts from the end of the Image and spans the full width
        Divider(
            color = colorResource(id = R.color.silver),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth() // Make the Divider take up the full width of the parent
                .padding(top = 20.dp) // Add padding as needed
                .align(Alignment.BottomCenter) // Align the Divider at the bottom center of the Box
                .offset(x = 85.dp) // Set the offset based on the width of the Image (60.dp + 15.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        ItemsList()
    }
}

