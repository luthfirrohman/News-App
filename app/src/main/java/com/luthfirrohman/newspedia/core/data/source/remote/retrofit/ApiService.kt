package com.luthfirrohman.newspedia.core.data.source.remote.retrofit

import com.luthfirrohman.newspedia.core.data.source.remote.response.NewsResponse
import com.luthfirrohman.newspedia.utils.Constant
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // Based on requirements (Top-headlines_
    @GET("top-headlines?country=${Constant.COUNTRY_ID}")
    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>
}