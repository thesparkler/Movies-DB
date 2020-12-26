package com.amitkumar.moviesdb.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET

data class MovieListResponse(
        @SerializedName("page") val page: Int,
        @SerializedName("results") val results: MutableList<Movie>,
        @SerializedName("total_pages") val total_pages: Int,
        @SerializedName("total_results") val total_results: Int
) {
    val res: MutableList<Movie>
        get() = results

}