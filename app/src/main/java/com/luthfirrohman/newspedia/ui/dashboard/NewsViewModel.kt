package com.luthfirrohman.newspedia.ui.dashboard

import androidx.lifecycle.ViewModel
import com.luthfirrohman.newspedia.core.data.NewsRepository
import com.luthfirrohman.newspedia.core.data.source.local.entity.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    // Mendapatkan Top-Headlines (Berita Utama)
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    // Mendapatkan Berita Tersimpan (LocalDB)
    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    // Add News to Bookmark Status
    fun saveNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, true)
    }

    fun deleteNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, false)
    }
}