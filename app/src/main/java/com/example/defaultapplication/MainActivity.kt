package com.example.defaultapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.defaultapplication.core.Utilities
import com.example.defaultapplication.databinding.ActivityMainBinding
import com.example.defaultapplication.core.observeEvent
import com.example.defaultapplication.ui.viewmodels.MovieViewModel
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.services.UIState
import com.example.defaultapplication.ui.adapters.MovieAdapter
import com.example.defaultapplication.ui.viewmodels.MovieBindingViewModel
import com.example.defaultapplication.ui.widgets.LoadingDialog

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    //viewModels
    private lateinit var movieViewModel: MovieViewModel
    private var movieBindingViewModel: MovieBindingViewModel? = null

    //adapters
    private lateinit var movieAdapter: MovieAdapter

    // Dialogs
    private var loadingDialog: LoadingDialog? = null

    private fun handlerGetMovie(status: UIState<MutableList<Movie>>) {
        when (status) {
            is UIState.OnLoading -> {
                showLoading("Cargando sus peliculas.")
            }

            is UIState.OnSuccess -> {
                hideLoading()
                val data = status.data
                if (!(data.isNullOrEmpty())){
                    //movieAdapter.setData(data)
                    movieBindingViewModel?.setCouponsInRecyclerAdapter(data)
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
        activityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        movieBindingViewModel = ViewModelProviders.of(this).get(MovieBindingViewModel::class.java)


        activityMainBinding.movieViewModel = movieBindingViewModel
        setContentView(activityMainBinding.root)

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel(this.application)::class.java)

        movieViewModel.getMoviesLiveData().observeEvent(this) {
            handlerGetMovie(it)
        }

        initAdapters()

        listenerEventsClick()
        movieBindingViewModel?.setContext(this)
    }

    private fun initAdapters() {
/*        movieAdapter = MovieAdapter(context = this,

            clickClosure = {
                Toast.makeText(
                    this,
                    "You clicked the moview ${it.title} - ${it.year}",
                    Toast.LENGTH_LONG
                ).show()
            },

            longClickClosure = {

            }
        )

        activityMainBinding.moviesRecyclerView.run {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            addItemDecoration(
                MyDividerItemDecoration(context, R.drawable.line_decoration)
            )
        }*/
    }

    private fun listenerEventsClick() {
        activityMainBinding.searchButton.onClickListener()

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
                activityMainBinding.searchButton.id -> {
                    if (!(activityMainBinding.textView.text.isNullOrEmpty())) {
                        movieViewModel.getMovies(
                            activityMainBinding.textView.text.toString().trim()
                        )

                        activityMainBinding.textView.hint = "test"

                    }
                }
            }
        }
    }
}