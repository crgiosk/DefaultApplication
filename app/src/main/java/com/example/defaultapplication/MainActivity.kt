package com.example.defaultapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.defaultapplication.UI.viewmodels.MovieViewModel
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.services.UIState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel

    private fun handlerGetMovie(status: UIState){
        when (status){
            is UIState.OnLoading -> {

            }

            is UIState.OnSuccess<*> -> {
                val data = (status.data as Array<Movie>).toMutableList()

            }

            is UIState.OnError -> {

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

        listenerEventsClick()
    }

    private fun listenerEventsClick() {
        searchButton.onClickListener()

    }

    private fun View.onClickListener(){
        this.setOnClickListener {
            when (it.id){
                searchButton.id -> {
                    if (!(textView.text.isNullOrEmpty())){
                        movieViewModel.getMovies(textView.text.toString().trim())
                    }
                }
            }
        }
    }
}