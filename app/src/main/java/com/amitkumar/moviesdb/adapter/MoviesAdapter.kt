package com.amitkumar.moviesdb.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.amitkumar.moviesdb.R
import com.amitkumar.moviesdb.model.Movie
import com.amitkumar.moviesdb.movies_list.MovieListActivity
import com.amitkumar.moviesdb.network.ApiClient
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.movie_card.view.*
import java.lang.String

class MoviesAdapter(movieListActivity: MovieListActivity, movieList: MutableList<Movie>) : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private var movieListActivity: MovieListActivity? = null
    private var movieList: List<Movie>? = null
    private var originalMovieList: List<Movie>? = null

    init {
        this.movieListActivity = movieListActivity
        this.movieList = movieList
        originalMovieList = movieList

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image = itemView.iv_movie_thumbnail
        var title = itemView.tv_movie_title
        var rlsDate = itemView.tv_release_date
        var rating = itemView.tv_movie_ratings
        var ploadImage = itemView.pb_load_image
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_card, parent, false)

        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return movieList!!.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var movie: Movie = movieList!!.get(position)

        holder.title.setText(movie.mTitle)
        holder.rating.setText(String.valueOf(movie.mVote))
        holder.rlsDate.setText(movie.rlsDate)

        // loading album cover using Glide library

        // loading album cover using Glide library
        Glide.with(movieListActivity!!)
                .load(ApiClient.IMAGE_BASE_URL + movie.mPoster)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(@Nullable e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        holder.ploadImage.setVisibility(View.GONE)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        holder.ploadImage.setVisibility(View.GONE)
                        return false
                    }
                })
                .apply(RequestOptions().placeholder(R.drawable.ic_place_holder).error(R.drawable.ic_place_holder))
                .into(holder.image)

    }
}