package com.example.defaultapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.defaultapplication.core.Utilities
import com.example.defaultapplication.ui.viewmodels.MovieViewModel
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.services.UIState
import com.example.defaultapplication.ui.adapters.MovieAdapter
import com.example.defaultapplication.ui.widgets.LoadingDialog
import com.example.defaultapplication.ui.widgets.MyDividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //viewModels
    private lateinit var movieViewModel: MovieViewModel

    //adapters
    private lateinit var movieAdapter: MovieAdapter

    // Dialogs
    private var loadingDialog: LoadingDialog? = null

    private fun handlerGetMovie(status: UIState) {
        when (status) {
            is UIState.OnLoading -> {
                showLoading("Cargando sus peliculas.")
            }

            is UIState.OnSuccess<*> -> {
                hideLoading()
                val data = (status.data as Array<Movie>).toMutableList()
                if (!(data.isNullOrEmpty())){
                    movieAdapter.setData(data)
                }else{
                    Utilities.showAlertDialog(this, "Empty data, try again.", R.string.title_alert_warning)
                }
            }

            is UIState.OnError -> {
                hideLoading()
                Utilities.showAlertDialog(this, status.error, R.string.title_error)
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

    /**
     * Show Loading
     */
    private fun showLoading(message: String) {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.newInstance(message)
            loadingDialog!!.show(supportFragmentManager, LoadingDialog.TAG)
        }
    }

    /**
     * Hide Loading
     */
    private fun hideLoading() {
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
            loadingDialog = null
        }
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