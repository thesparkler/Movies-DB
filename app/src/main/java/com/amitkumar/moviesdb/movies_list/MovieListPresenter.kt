package com.amitkumar.moviesdb.movies_list

import com.amitkumar.moviesdb.model.Movie

class MovieListPresenter(movieListView: MovieListContract.View?) : MovieListContract.Presenter, MovieListContract.Model.OnFinishedListener {

    private var movieListView: MovieListContract.View? = null

    private var movieListModel: MovieListContract.Model? = null

    init {
        this.movieListView = movieListView
        movieListModel = MovieListModel()
    }

//    override fun onFinished(movieArrayList: MutableList<Movie?>?) {
//        movieListView!!.setDataToRecyclerView(movieArrayList)
//        if (movieListView != null) {
//            movieListView!!.hideProgress()
//        }
//
//    }

    override fun onFinished(movieArrayList: MutableList<Movie>) {
        movieListView!!.setDataToRecyclerView(movieArrayList)
        if (movieListView != null) {
            movieListView?.hideProgress()
        }
    }

    override fun onFailure(t: Throwable?) {
        movieListView!!.onResponseFailure(t!!)
        if (movieListView != null) {
            movieListView!!.hideProgress()
        }
    }

    override fun onDestroy() {
        movieListView = null
    }

    override fun getMoreData(pageNo: Int) {
        if (movieListView != null) {
            movieListView!!.showProgress()
        }
        movieListModel!!.getMovieList(this, pageNo)

    }

    override fun requestDataFromServer() {
        if (movieListView != null) {
            movieListView?.showProgress()
        }
        movieListModel!!.getMovieList(this, 1)

    }
}