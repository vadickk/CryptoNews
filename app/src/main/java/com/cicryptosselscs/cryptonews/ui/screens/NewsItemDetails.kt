package com.cicryptosselscs.cryptonews.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.cicryptosselscs.cryptonews.R
import com.cicryptosselscs.cryptonews.firebase.NewsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItemDetails(newsItem: NewsItem, onBack: () -> Unit, onClick: (NewsItem) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.it_news))
                },
                navigationIcon = {
                    IconButton(onClick = { onBack.invoke() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                            id = R.string.back
                        ))
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = newsItem.articleImgUrl,
                    stringResource(id = R.string.banner)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Author: ${newsItem.author ?: stringResource(id = R.string.unknown)}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Time: ${newsItem.time ?: stringResource(id = R.string.unknown)}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Article URL: ${newsItem.articleUrl ?: stringResource(id = R.string.not_available)}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        onClick.invoke(newsItem)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.next))
                }
            }
        }
    )
}
