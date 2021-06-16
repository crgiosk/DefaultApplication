package com.example.defaultapplication.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.models.MovieModel
import com.example.defaultapplication.services.Event
import com.example.defaultapplication.services.ModelResponse
import com.example.defaultapplication.services.UIState

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val model = MovieModel()

    private val getMoviesMutableLiveData: MutableLiveData<Event<UIState<MutableList<Movie>>>> = MutableLiveData()

    fun getMoviesLiveData(): LiveData<Event<UIState<MutableList<Movie>>>> = getMoviesMutableLiveData

    fun getMovies(title: String) {
        getMoviesMutableLiveData.value = Event(UIState.OnLoading)
        model.getMovieByTitle(title) { response ->
            when (response){
                is ModelResponse.OnSuccess -> {
                    getMoviesMutableLiveData.postValue(Event(UIState.OnSuccess(response.result.toMutableList())))
                }

                is ModelResponse.OnError -> {
                    getMoviesMutableLiveData.postValue(Event(UIState.OnError(response.error)))
                }
            }
        }
    }
}