package com.luthfirrohman.newspedia.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.luthfirrohman.newspedia.core.data.source.local.entity.NewsEntity
import com.luthfirrohman.newspedia.core.data.source.local.room.NewsDao
import com.luthfirrohman.newspedia.core.data.source.remote.response.NewsResponse
import com.luthfirrohman.newspedia.core.data.source.remote.retrofit.ApiService
import com.luthfirrohman.newspedia.utils.AppExecutors
import com.luthfirrohman.newspedia.utils.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    fun getHeadlineNews(): LiveData<Result<List<NewsEntity>>> {
        result.value = Result.Loading
        // API
        val client = apiService.getNews(Constant.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            val isBookmarked = article?.title?.let { newsDao.isNewsBookmarked(it) }
                            val news = isBookmarked?.let {
                                NewsEntity(
                                    article.title,
                                    article.publishedAt,
                                    article.urlToImage,
                                    article.url,
                                    article.description,
                                    article.source?.name,
                                    article.author,
                                    it
                                )
                            }
                            if (news != null) {
                                newsList.add(news)
                            }
                        }
                        newsDao.deleteAll()
                        newsDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        //Local DB
        val localData = newsDao.getNews()

        result.addSource(localData) { newData: List<NewsEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    fun setBookmarkedNews(news: NewsEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            news.isBookmarked = bookmarkState
            newsDao.updateNews(news)
        }
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also { instance = it }
    }
}