package com.example.defaultapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.defaultapplication.core.Utilities
import com.example.defaultapplication.core.observeEvent
import com.example.defaultapplication.core.parseJsonStringToClass
import com.example.defaultapplication.entities.Alphabet
import com.example.defaultapplication.entities.ContactResponse
import com.example.defaultapplication.ui.viewmodels.MovieViewModel
import com.example.defaultapplication.entities.Movie
import com.example.defaultapplication.services.ConnectionResponse
import com.example.defaultapplication.services.ModelResponse
import com.example.defaultapplication.services.UIState
import com.example.defaultapplication.ui.adapters.MovieAdapter
import com.example.defaultapplication.ui.widgets.LoadingDialog
import com.example.defaultapplication.ui.widgets.MyDividerItemDecoration
import com.mvs.androidsearchindex.AlphabetAdapter
import com.mvs.androidsearchindex.MainListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Locale
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    //viewModels
    private lateinit var movieViewModel: MovieViewModel

    //adapters
    private lateinit var movieAdapter: MovieAdapter

    // Dialogs
    private var loadingDialog: LoadingDialog? = null

    private lateinit var alphabetsList: MutableList<Alphabet>

    private lateinit var contentList: List<ContactResponse>
    private var recyclerViewContent: RecyclerView? = null

    private var recyclerViewAlphabet: RecyclerView? = null
    private var alphabetAdapter: AlphabetAdapter? = null
    private var contenAdapter: MainListAdapter? = null

    private fun handlerGetMovie(status: UIState<MutableList<Movie>>) {
        when (status) {
            is UIState.OnLoading -> {
                showLoading("Cargando sus peliculas.")
            }

            is UIState.OnSuccess -> {
                hideLoading()
                val data = status.data
                if (!(data.isNullOrEmpty())) {
                    movieAdapter.setData(data)
                } else {
                    Utilities.showAlertDialog(
                        this,
                        "Empty data, try again.",
                        R.string.title_alert_warning
                    )
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

        movieViewModel =
            ViewModelProviders.of(this).get(MovieViewModel(this.application)::class.java)

        movieViewModel.getMoviesLiveData().observeEvent(this) {
            handlerGetMovie(it)
        }


        val response: (ConnectionResponse) = ConnectionResponse.OnSuccess(
            "{" +
                    "\"status\": true," +
                    "\"data\": [" +
                    "{" +
                    "\"id\": \"5f500e6fb0569b880f510314\"," +
                    "\"first_name\": \"Renzon\"," +
                    "\"last_name\": \"Caceres\"," +
                    "\"name\": \"Renzon Caceres\"," +
                    "\"user_name\": \"renzon.caceres\"," +
                    "\"email\": \"renzon.caceres@wiedii.co\"," +
                    "\"picture\": \"osorio.png\"," +
                    "\"is_connected\": 1" + "}," +
                    "{" +
                    "\"id\": \"5f5169deb0569b880f5356e6\"," +
                    "\"first_name\": \"Andres\"," +
                    "\"last_name\": \"Arenales\"," +
                    "\"name\": \"Andres Arenales\"," +
                    "\"user_name\": \"andres.arenales\"," +
                    "\"email\": \"andres.arenales@wiedii.co\"," +
                    "\"picture\": \"user.png\"," +
                    "\"is_connected\": 0" + "}," +
                    "{" + "\"id\": \"5f88947bb0569b880f76bbff\"," +
                    "\"first_name\": \"Paulo\"," +
                    "\"last_name\": \"Coelho\"," +
                    "\"name\": \"Paulo Coelho\"," +
                    "\"user_name\": \"paulo.coelho\"," +
                    "\"email\": \"paulo.coelho@wiedii.co\"," +
                    "\"picture\": \"user.png\"," +
                    "\"is_connected\": 0" +
                    "}," +
                    "{" + "\"id\": \"5f889696b0569b880f76bd00\"," +
                    "\"first_name\": \"Dead\"," +
                    "\"last_name\": \"Pool\"," +
                    "\"name\": \"Dead Pool\"," +
                    "\"user_name\": \"DeadPool\"," +
                    "\"email\": \"deadpool@wiedii.co\"," +
                    "\"picture\": \"user.png\"," +
                    "\"is_connected\": 0" + "}," +
                    "{ " +
                    "\"id\": \"5f88a52eb0569b880f76c44b\"," +
                    "\"first_name\": \"Tomas\"," +
                    "\"last_name\": \"Edinson\"," +
                    "\"name\": \"Tomas  Edinson\"," +
                    "\"user_name\": \"tomas.edinson\"," +
                    "\"email\": \"tomas.edinson@wiedii.co\"," +
                    "\"picture\": \"user.png\"," +
                    "\"is_connected\": 0 }" +
                    "]," +
                    "\"error\": null " + "}"
        )

        val dataContent = mutableListOf<ContactResponse>()
        if (response is ConnectionResponse.OnSuccess) {
            val list = response.result.parseJsonStringToClass<Array<ContactResponse>>("123")
            when (list) {
                is ModelResponse.OnSuccess -> {
                    val data = mutableListOf<ContactResponse>()
                    data.addAll(list.result)
                    for (i in 'a'..'z') {
                        val item = list.result[Random.nextInt(list.result.indices)]
                        data.add(
                            ContactResponse(
                                firstName = "$i${item.firstName}",
                                id = "$i${item.id}$item"
                            )
                        )
                    }
                    dataContent.addAll(data)
                }

                is ModelResponse.OnError -> {
                    print("Error, Incompatible data ${list.error}")
                }
            }
        }
        contentList = dataContent.asSequence()
            .sortedBy { it.firstName.toLowerCase(Locale.ROOT) }
            .toMutableList()
        alphabetsList = mutableListOf()
        contentList.asSequence()
            .map { it.firstName.toLowerCase(Locale.ROOT) }
            .map { it.first() }
            .distinctBy { it }
            .sortedBy { it }.toMutableList().forEach {
                alphabetsList.add(Alphabet(it))
            }

        println("mvs123 ...................  ")

        println("mvs123   ${contentList.size}")
        println("mvs123   $alphabetsList")
        intiViews()
        //initAdapters()

        listenerEventsClick()
    }

    private fun intiViews() {

        recyclerViewAlphabet = findViewById(R.id.rv_alphabets)
        alphabetAdapter = AlphabetAdapter(this) {
            Log.i("MVS", it.char + "")
            Log.i("MVS", contentList.toString())
            runOnUiThread {
                val data = contentList.indexOfFirst { it2 ->
                    it2.firstName.startsWith(
                        it.char,
                        ignoreCase = true
                    )
                }
                val layoutManager: LinearLayoutManager = recyclerViewContent?.layoutManager as LinearLayoutManager
                layoutManager.scrollToPositionWithOffset(data, 0)
            }
        }
        alphabetAdapter?.setData(alphabetsList)
        recyclerViewAlphabet?.layoutManager = LinearLayoutManager(this)
        recyclerViewAlphabet?.adapter = alphabetAdapter


        recyclerViewContent = findViewById(R.id.rv__main_list)
        contenAdapter = MainListAdapter(contentList, this)

        val lManager = LinearLayoutManager(this)
        lManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewContent?.layoutManager = lManager

        recyclerViewContent?.adapter = contenAdapter
        val listner = CustomScrollListener()

        rv__main_list.addOnScrollListener(listner)

    }

    private fun initAdapters() {
        movieAdapter = MovieAdapter(context = this,

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
                        movieViewModel.getMovies(
                            textView.text.toString().trim()
                        )

                        textView.hint = "test"

                    }
                }
            }
        }
    }

    private var lastUSed: ContactResponse? = null

    fun onScroll() {

        runOnUiThread {
            val layoutManager: LinearLayoutManager = recyclerViewContent?.layoutManager as LinearLayoutManager
            val item = contenAdapter?.getItemByPosition(layoutManager.findFirstVisibleItemPosition())
            val findFirstVisibleItemPosition = contenAdapter?.getItemByPosition(layoutManager.findFirstVisibleItemPosition())
            //we use findFirstCompletelyVisibleItemPosition
            val findFirstCompletelyVisibleItemPosition = contenAdapter?.getItemByPosition(layoutManager.findFirstCompletelyVisibleItemPosition())
            Log.e("itemsFindBy", "\n\n findFirstVisibleItemPosition ${findFirstVisibleItemPosition?.firstName?.toLowerCase()} \n\n")
            Log.e("itemsFindBy","findFirstCompletelyVisibleItemPosition ${findFirstCompletelyVisibleItemPosition?.firstName?.toLowerCase()}\n")
            if (lastUSed == null) {
                lastUSed = item
            } else {
                if (alphabetAdapter?.getSelected() != null) {
                    if (item?.firstName?.first()?.toLowerCase() != alphabetAdapter?.getSelected()?.char?.toLowerCase()) {
                        Log.e(TAG, "\nCambiar letra -> \ncontact ${item?.firstName?.toLowerCase()} \nalphabet ${alphabetAdapter?.getSelected()?.char?.toLowerCase()}")
                    } else {
                        Log.e(TAG, "\niguales letras -> \ncontact ${item?.firstName?.toLowerCase()} \nalphabet ${alphabetAdapter?.getSelected()?.char?.toLowerCase()}"
                        )
                    }
                }
                if (item?.id != lastUSed?.id) {
                    lastUSed = item
                }
            }
        }
    }

    inner class CustomScrollListener : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE -> println("The RecyclerView is not scrolling")
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    onScroll()
                    //println("Scrolling now")
                }
                RecyclerView.SCROLL_STATE_SETTLING -> println("Scroll Settling")
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            when {
                dx > 0 -> {
                    println("Scrolled Right")
                }
                dx < 0 -> {
                    println("Scrolled Left")
                }
                else -> {
                    println("No Horizontal Scrolled")
                }
            }
            when {
                dy > 0 -> {
                    onScroll()
                    println("Scrolled Downwards")
                }
                dy < 0 -> {
                    println("Scrolled Upwards")
                }
                else -> {
                    println("No Vertical Scrolled")
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
    }
}