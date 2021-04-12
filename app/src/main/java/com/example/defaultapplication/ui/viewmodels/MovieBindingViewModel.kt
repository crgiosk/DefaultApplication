package com.example.defaultapplication.ui.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.defaultapplication.R
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.ui.adapters.MovieAdapter
import com.example.defaultapplication.ui.adapters.MovieBindingAdapter

class MovieBindingViewModel: ViewModel() {

    private val items: MutableList<Movie> = mutableListOf()
    private lateinit var context: Context
    private var recyclerCouponsAdapter: MovieBindingAdapter? = null

    fun setCouponsInRecyclerAdapter(movies: MutableList<Movie>){
        items.clear()
        items.addAll(movies)
        recyclerCouponsAdapter?.setData(items)
    }

    fun setContext(context: Context){
        this.context = context
    }

    fun getRecyclerCouponsAdapter(): MovieBindingAdapter?{
        recyclerCouponsAdapter = MovieBindingAdapter(this, R.layout.item_binding_movie)
        return recyclerCouponsAdapter
    }

    fun getMovieAt(position: Int): Movie {
        return items[position]
    }

}