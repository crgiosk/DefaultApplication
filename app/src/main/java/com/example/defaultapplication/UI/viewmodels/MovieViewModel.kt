package com.example.defaultapplication.UI.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.models.MovieModel
import com.example.defaultapplication.services.ModelResponse
import com.example.defaultapplication.services.UIState

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val model = MovieModel()

    private val getMoviesMutableLiveData: MutableLiveData<UIState> = MutableLiveData()


    fun getMoviesLiveData(): LiveData<UIState> = getMoviesMutableLiveData

    fun getMovies(title: String) {
        getMoviesMutableLiveData.value = UIState.OnLoading
        model.getMovieByTitle(title) { response ->
            when (response){
                is ModelResponse.OnSuccess<*> -> {
                    getMoviesMutableLiveData.postValue(UIState.OnSuccess(response.result))
                }

                is ModelResponse.OnError -> {
                    getMoviesMutableLiveData.postValue(UIState.OnSuccess(response.error))
                }
            }
        }
    }
}