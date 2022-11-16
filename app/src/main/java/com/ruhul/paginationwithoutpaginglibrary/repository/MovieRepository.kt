package com.ruhul.paginationwithoutpaginglibrary.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ruhul.paginationwithoutpaginglibrary.repository.model.Item
import com.ruhul.paginationwithoutpaginglibrary.repository.model.ItemResponse
import com.ruhul.paginationwithoutpaginglibrary.repository.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    private val responseLiveData = MutableLiveData<List<Item>>()
    val responseProgressStatus = MutableLiveData<Boolean>()

    private val log = "MovieRepository"


    fun getMovieList(): MutableLiveData<List<Item>> {
        val call = RetrofitInstance.getApi().getMovies()
        call.enqueue(object : Callback<ItemResponse> {

            override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                Log.v(log, "call failed")
                responseProgressStatus.postValue(false)
            }

            override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
                if (response.isSuccessful) {
                    Log.v(log, "success")
                    responseLiveData.postValue(response.body()?.items)
                    responseProgressStatus.postValue(true)

                }

            }

        })
        return responseLiveData

    }
}