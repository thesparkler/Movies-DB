package com.amitkumar.moviesdb.movies_list

import android.util.Log
import com.amitkumar.moviesdb.model.Movie
import com.amitkumar.moviesdb.model.MovieListResponse
import com.amitkumar.moviesdb.network.ApiClient
import com.amitkumar.moviesdb.network.ApiClient.Companion.API_KEY
import com.amitkumar.moviesdb.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListModel : MovieListContract.Model {

    private val TAG = "MovieListModel"

    /**
     * This function will fetch movies data
     *@param onFinishedListener
     *@param pageNo: Which page to load.
     */
    override fun getMovieList(onFinishedListener: MovieListContract.Model.OnFinishedListener?, pageNo: Int) {

        val apiService: ApiInterface = ApiClient.client.create(ApiInterface::class.java)

        val call: Call<MovieListResponse?>? = apiService.getPopularMovies(API_KEY, pageNo)

        call!!.enqueue(object : Callback<MovieListResponse?> {
            override fun onResponse(call: Call<MovieListResponse?>, response: Response<MovieListResponse?>) {
                val movies: MutableList<Movie> = response.body()!!.res
                Log.d(TAG, "No. of movies received:" + movies.size)
                onFinishedListener?.onFinished(movies)
            }

            override fun onFailure(call: Call<MovieListResponse?>, t: Throwable) {
                // Log error here since request failed
                Log.e(TAG, t.toString())
                onFinishedListener!!.onFailure(t)

            }
        })


    }
}