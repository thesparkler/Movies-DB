package com.amitkumar.moviesdb.movies_list

import com.amitkumar.moviesdb.model.Movie

interface MovieListContract {

    interface Model {
        interface OnFinishedListener {
            fun onFinished(movieArrayList: MutableList<Movie>)
            fun onFailure(t: Throwable?)
        }

        fun getMovieList(onFinishedListener: OnFinishedListener?, pageNo: Int)

    }

    interface View {
        fun showProgress()

        fun hideProgress()

        fun setDataToRecyclerView(movieArrayList: MutableList<Movie>)

        fun onResponseFailure(t: Throwable)

    }

    interface Presenter {
        fun onDestroy()

        fun getMoreData(pageNo: Int)

        fun requestDataFromServer()
    }
}