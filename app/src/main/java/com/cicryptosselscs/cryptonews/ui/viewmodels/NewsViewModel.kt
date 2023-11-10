package com.cicryptosselscs.cryptonews.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cicryptosselscs.cryptonews.firebase.NewsItem
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ViewModel() {
    var news = MutableStateFlow(emptyList<NewsItem>())
        private set

    fun getAllNews() {
        viewModelScope.launch {
            try {
                val querySnapshot = firebaseFirestore.collection("news").get().await()
                news.value = querySnapshot.toObjects<NewsItem>()
            } catch (e: Exception) {
                e.printStackTrace()
                news.value = emptyList()
            }
        }
    }
}
