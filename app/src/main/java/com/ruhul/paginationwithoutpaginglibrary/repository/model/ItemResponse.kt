package com.ruhul.paginationwithoutpaginglibrary.repository.model

import com.google.gson.annotations.SerializedName

class ItemResponse(
    @SerializedName("Response")
    val response: String,

    @SerializedName("Search")
    val items: List<Item>,

    @SerializedName("totalResults")
    val totalResults: String
)