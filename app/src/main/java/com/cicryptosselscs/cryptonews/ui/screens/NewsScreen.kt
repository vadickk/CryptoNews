package com.cicryptosselscs.cryptonews.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.cicryptosselscs.cryptonews.R
import com.cicryptosselscs.cryptonews.firebase.NewsItem
import com.cicryptosselscs.cryptonews.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    onNewsItemClick: (NewsItem) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {
    viewModel.getAllNews()
    val newsList = viewModel.news.collectAsState().value.shuffled()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.news)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White
                )
            )
        },
        content = { paddings ->
            NewsList(newsList = newsList, onNewsItemClick = onNewsItemClick, paddings.calculateTopPadding())
        }
    )
}

@Composable
fun NewsList(newsList: List<NewsItem>, onNewsItemClick: (NewsItem) -> Unit, topPadding: Dp) {
    LazyColumn(
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, top = topPadding + 6.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(newsList) { news ->
            NewsItemCard(news = news, onNewsItemClick = onNewsItemClick)
        }
    }
}

@Composable
fun NewsItemCard(news: NewsItem, onNewsItemClick: (NewsItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNewsItemClick(news) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            AsyncImage(
                model = news.articleImgUrl,
                stringResource(id = R.string.banner)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = news.title ?: "", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Default.AccountCircle, contentDescription = null)
                Text(text = news.author ?: stringResource(id = R.string.not_available))
                Icon(Icons.Default.Info, contentDescription = null)
                Text(text = news.time ?: stringResource(id = R.string.not_available))
            }
        }
    }
}
