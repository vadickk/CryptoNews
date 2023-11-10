package com.cicryptosselscs.cryptonews.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.cicryptosselscs.cryptonews.firebase.NewsItem
import com.cicryptosselscs.cryptonews.ui.screens.NewsItemDetails
import com.cicryptosselscs.cryptonews.ui.screens.NewsScreen
import com.cicryptosselscs.cryptonews.utils.NavigationRoutes
import com.cicryptosselscs.cryptonews.themes.theme.CryptoNewsTheme
import com.cicryptosselscs.cryptonews.ui.screens.NewsItemDetailsFinish
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoNewsTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = NavigationRoutes.NEWS_SCREEN
                ) {
                    composable(NavigationRoutes.NEWS_SCREEN) {
                        NewsScreen(
                            onNewsItemClick = { newsItem ->
                                val jsonNewsItem = Gson().toJson(newsItem)
                                val encode = URLEncoder.encode(jsonNewsItem, StandardCharsets.UTF_8.toString())
                                navController.navigate("${NavigationRoutes.NEWS_DETAILS_SCREEN}/${encode}")
                            }
                        )
                    }
                    composable(
                        route = "${NavigationRoutes.NEWS_DETAILS_SCREEN}/{newsItem}",
                        arguments = listOf(navArgument("newsItem") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val jsonNewsItem = backStackEntry.arguments?.getString("newsItem")?.replace("+", " ")
                        val newItem = Gson().fromJson(jsonNewsItem, NewsItem::class.java)
                        if (newItem != null) {
                            NewsItemDetails(
                                newsItem = newItem,
                                onBack = { navController.popBackStack() },
                                onClick = { newsItem ->
                                    val jsonNewsItem1 = Gson().toJson(newsItem)
                                    val encode = URLEncoder.encode(jsonNewsItem1, StandardCharsets.UTF_8.toString())
                                    navController.navigate("${NavigationRoutes.NEWS_DETAILS_FINISH_SCREEN}/${encode}")
                                }
                            )
                        }
                    }

                    composable(
                        route = "${NavigationRoutes.NEWS_DETAILS_FINISH_SCREEN}/{newsItem}",
                        arguments = listOf(navArgument("newsItem") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val jsonNewsItem = backStackEntry.arguments?.getString("newsItem")?.replace("+", " ")
                        val newItem = Gson().fromJson(jsonNewsItem, NewsItem::class.java)
                        if (newItem != null) {
                            NewsItemDetailsFinish(newsItem = newItem) {
                                navController.popBackStack()
                            }
                        }
                    }
                }
            }
        }
    }
}
