package com.amitkumar.moviesdb.model

import com.google.gson.annotations.SerializedName

data class Movie(

        val id: Int,
        val title: String,
        @SerializedName("original_title") val original_title: String,
        @SerializedName("release_date") val releaseDate: String,
        @SerializedName("vote_average") val rating: Float,
        @SerializedName("poster_path") val thumbPath: String,
        @SerializedName("overview") val overview: String,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("credits") val credits: String,
        @SerializedName("runtime") val runTime: String,
        @SerializedName("tagline") val tagline: String,
        @SerializedName("homepage") val homepage: String

) {

    val rlsDate: String
        get() = releaseDate

    val mTitle: String
        get() = title

    val mVote: Float
        get() = rating

    val mPoster: String
        get() = thumbPath

    val mOverView: String
        get() = overview

}
