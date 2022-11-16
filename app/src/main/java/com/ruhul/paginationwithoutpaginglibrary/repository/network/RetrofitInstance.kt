package com.ruhul.paginationwithoutpaginglibrary.repository.network

import com.ruhul.paginationwithoutpaginglibrary.repository.model.Utils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    private fun getRetrofit(): Retrofit {

        val interceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl(Utils.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getApi(): ApiServices {
        return getRetrofit().create(ApiServices::class.java)
    }


}