package com.example.defaultapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.defaultapplication.ui.viewmodels.MovieViewModel
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.services.UIState
import com.example.defaultapplication.ui.adapters.MovieAdapter
import com.example.defaultapplication.ui.widgets.MyDividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //viewModels
    private lateinit var movieViewModel: MovieViewModel

    //adapters
    private lateinit var movieAdapter: MovieAdapter

    private fun handlerGetMovie(status: UIState) {
        when (status) {
            is UIState.OnLoading -> {

            }

            is UIState.OnSuccess<*> -> {
                val data = (status.data as Array<Movie>).toMutableList()
                movieAdapter.setData(data)
            }

            is UIState.OnError -> {
                Toast.makeText(
                    this,
                    "Error ${status.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel(this.application)::class.java)

        movieViewModel.getMoviesLiveData().observe(this, Observer {
            handlerGetMovie(it)
        })

        initAdapters()

        listenerEventsClick()
    }

    private fun initAdapters() {
        movieAdapter = MovieAdapter(context = this,

            clickClosure = { movie ->
                Toast.makeText(
                    this,
                    "You clicked the moview ${movie.title} - ${movie.year}",
                    Toast.LENGTH_LONG
                ).show()
            },

            longClickClosure = {

            }
        )

        moviesRecyclerView.run {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(
                MyDividerItemDecoration(context, R.drawable.line_decoration)
            )
        }
    }

    private fun listenerEventsClick() {
        searchButton.onClickListener()

    }

    private fun View.onClickListener() {
        this.setOnClickListener {
            when (it.id) {
                searchButton.id -> {
                    if (!(textView.text.isNullOrEmpty())) {
                        movieViewModel.getMovies(textView.text.toString().trim())
                    }
                }
            }
        }
    }
}