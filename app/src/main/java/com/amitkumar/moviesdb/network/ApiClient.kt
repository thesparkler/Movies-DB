package com.amitkumar.moviesdb.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        const val BASE_URL = "http://api.themoviedb.org/3/"
        const val API_KEY = "34cb0ee49c4ff006be7f74d9f5a16d3b"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200/"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780/"

        private var retrofit: Retrofit? = null

        val client: Retrofit
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BASE_URL)
                            .build()
                }
                return retrofit!!
            }
    }

}