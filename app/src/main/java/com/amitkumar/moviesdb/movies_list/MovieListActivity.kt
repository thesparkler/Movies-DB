package com.amitkumar.moviesdb.movies_list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amitkumar.moviesdb.R
import com.amitkumar.moviesdb.adapter.MoviesAdapter
import com.amitkumar.moviesdb.model.Movie
import com.amitkumar.moviesdb.utils.GridSpacingItemDecoration
import com.amitkumar.moviesdb.utils.GridSpacingItemDecoration.Companion.dpToPx
import kotlinx.android.synthetic.main.activity_movie_list.*
import java.util.*

class MovieListActivity : AppCompatActivity(), MovieListContract.View, ShowEmptyView {

    private val TAG = "MovieListActivity"
    private var movieListPresenter: MovieListPresenter? = null

    //  private var rvMovieList: RecyclerView? = null
    private var moviesList: MutableList<Movie>? = null
    private var moviesAdapter: MoviesAdapter? = null

    // private var pbLoading: ProgressBar? = null
    private var tvEmptyView: TextView? = null

    private var pageNo = 1

    //Constants for load more
    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 5
    var firstVisibleItem = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    private var mLayoutManager: GridLayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        supportActionBar!!.title = getString(R.string.most_popular_movies)

        initUI()


        //Initializing presenter
        movieListPresenter = MovieListPresenter(this)

        movieListPresenter?.requestDataFromServer()

        setListeners()


    }

    /**
     * This method will initialize the UI components
     */
    private fun initUI() {
        moviesList = ArrayList()
        moviesAdapter = MoviesAdapter(this, moviesList as ArrayList<Movie>)
        mLayoutManager = GridLayoutManager(this, 2)
        rv_movie_list?.setLayoutManager(mLayoutManager)
        rv_movie_list?.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(this, 10), true))
        rv_movie_list?.setItemAnimator(DefaultItemAnimator())
        rv_movie_list?.adapter = moviesAdapter
    }

    override fun showProgress() {
        pb_loading?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_loading?.visibility = View.GONE

    }

    override fun setDataToRecyclerView(movieArrayList: MutableList<Movie>) {
        moviesList!!.addAll(movieArrayList)
        moviesAdapter!!.notifyDataSetChanged()

        // This will help us to fetch data from next page no.
        pageNo++

    }

    override fun onResponseFailure(t: Throwable) {
        Log.e(TAG, t.message)
        Toast.makeText(this, getString(R.string.communication_error), Toast.LENGTH_LONG).show()

    }

    override fun showEmptyView() {
        rv_movie_list!!.visibility = View.GONE
        tvEmptyView!!.visibility = View.VISIBLE

    }

    override fun hideEmptyView() {
        rv_movie_list!!.visibility = View.VISIBLE
        tvEmptyView!!.visibility = View.GONE

    }


    override fun onDestroy() {
        super.onDestroy()
        movieListPresenter!!.onDestroy()
    }

    fun setListeners() {
        rv_movie_list?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (recyclerView != null) {
                    super.onScrolled(recyclerView, dx, dy)
                }
                visibleItemCount = rv_movie_list!!.childCount
                totalItemCount = mLayoutManager!!.itemCount
                firstVisibleItem = mLayoutManager!!.findFirstVisibleItemPosition()

                // Handling the infinite scroll
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount
                    <= firstVisibleItem + visibleThreshold
                ) {
                    movieListPresenter!!.getMoreData(pageNo)
                    loading = true
                }

            }
        })
    }
}

