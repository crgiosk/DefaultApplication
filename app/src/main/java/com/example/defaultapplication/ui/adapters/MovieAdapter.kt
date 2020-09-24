package com.example.defaultapplication.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.example.defaultapplication.R
import com.example.defaultapplication.core.GlideApp
import com.example.defaultapplication.entities.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieAdapter(
    private val context: Context,
    val clickClosure: (Movie) -> Unit,
    val longClickClosure: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val moviesList: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }


    fun setData(data: MutableList<Movie>){
        this.moviesList.clear()
        this.moviesList.addAll(data)
        //if you delete this line, data wont show
        //notifyDataSetChanged()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(this.moviesList[position])
    }

    override fun getItemCount(): Int {
        return this.moviesList.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Movie) {

            //bindData
            itemView.titleMovieTextView.text = item.title
            itemView.yearMovieTextView.text = item.year
            itemView.typeMovieTextView.text = item.type

            //bindImage
            val options = RequestOptions()
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .circleCrop()

            GlideApp.with(context)
                .load(item.poster)
                .apply(options)
                .into(itemView.posterMovieImageView)

            itemView.setOnClickListener {
                clickClosure(item)
            }

        }
    }
}