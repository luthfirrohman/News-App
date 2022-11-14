package com.luthfirrohman.newspedia.core.di

import android.content.Context
import com.luthfirrohman.newspedia.core.data.source.local.room.NewsDatabase
import com.luthfirrohman.newspedia.core.data.NewsRepository
import com.luthfirrohman.newspedia.core.data.source.remote.retrofit.ApiConfig
import com.luthfirrohman.newspedia.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}