package com.cicryptosselscs.cryptonews.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val id: Int = 0,
    val author: String? = null,
    val time: String? = null,
    val articleUrl: String? = null,
    val articleImgUrl: String? = null,
    val title: String? = null
) : Parcelable
