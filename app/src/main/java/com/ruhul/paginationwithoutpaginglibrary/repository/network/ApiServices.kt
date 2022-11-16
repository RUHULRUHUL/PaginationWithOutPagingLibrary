package com.ruhul.paginationwithoutpaginglibrary.repository.network

import com.ruhul.paginationwithoutpaginglibrary.repository.model.ItemResponse
import com.ruhul.paginationwithoutpaginglibrary.repository.model.Utils
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET(Utils.movieItem)
    fun getItems(
    ): Response<ItemResponse>

    @GET(Utils.movieItem)
    fun getMovies(
    ): Call<ItemResponse>

}