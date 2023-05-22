package com.delirium.playlistmaker.search

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.SettingPreferences
import com.delirium.playlistmaker.search.model.SongItemButton
import com.delirium.playlistmaker.search.model.SongItemTitle
import com.delirium.playlistmaker.search.itunes.ITunesSetting
import com.delirium.playlistmaker.search.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), ClickListener {
    lateinit var editSearch: EditText
    lateinit var crossForDelete: ImageView
    private var inputTextSave: String = ""
    lateinit var recycler: RecyclerView
    private var data: MutableList<AdapterModel> = mutableListOf()
    private val adapter = AdapterSongs(this)
    private lateinit var songHistory: SongHistory

    private var isSearchSubmitted: Boolean = false
    private val searchRunnable = Runnable {
        getSongsITunes(inputTextSave)
    }
    private var isClickAllowed = true
    private var handler = Handler(Looper.getMainLooper())
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val toolbar = findViewById<Toolbar>(R.id.toolBarSearch)
        setSupportActionBar(toolbar)
        songHistory =
            SongHistory(getSharedPreferences(SettingPreferences.FINDING_SONG.name, MODE_PRIVATE))

        crossForDelete = findViewById(R.id.clear_search)

        recycler = findViewById(R.id.recycler_songs)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        crossForDelete.setOnClickListener { it ->
            editSearch.text.clear()
            it.visibility = View.INVISIBLE

            this.currentFocus?.let {
                val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(it.windowToken, 0)
            }
            renderHistory()
            isSearchSubmitted = false
        }

        editSearch = findViewById(R.id.edit_search)
        editSearch.addTextChangedListener(createTextWatcher())
        /*editSearch.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                getSongsITunes(inputTextSave)
                true
            } else {
                false
            }
        }*/
        editSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && editSearch.text.isEmpty()) {
                renderHistory()
            }
        }

        progressBar = findViewById(R.id.progress_bar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputString = savedInstanceState.getString(EDIT_TEXT)
        isSearchSubmitted = savedInstanceState.getBoolean(IS_SEARCH_SUBMITTED)
        editSearch.setText(inputString)
        if (isSearchSubmitted) getSongsITunes(inputTextSave)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT, inputTextSave)
        outState.putBoolean(IS_SEARCH_SUBMITTED, isSearchSubmitted)
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            crossForDelete.visibility = View.VISIBLE
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            progressBar.visibility = View.VISIBLE
            recycler.visibility = View.INVISIBLE
            inputTextSave = editSearch.text.toString()
            if (editSearch.hasFocus() && editSearch.text.isEmpty()) {
                renderHistory()
            } else {
                searchDebounce()
            }
            isSearchSubmitted = true
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun getSongsITunes(nameSong: String) {
        val request = ITunesSetting.itunesInstant

        request.getSongs(nameSong).enqueue(object : Callback<DataITunes> {
            override fun onFailure(call: Call<DataITunes>, t: Throwable) {
                t.printStackTrace()
                data.clear()
                data.add(
                    ErrorItem(
                        text = getString(R.string.not_connect_item_text),
                        textSub = getString(R.string.not_connect_item_text_sub)
                    )
                )
//                isSearchSubmitted = true
                updateData()
            }

            override fun onResponse(
                call: Call<DataITunes>,
                response: Response<DataITunes>
            ) {
                if (response.isSuccessful) {
                    data.clear()
                    val rawData = response.body()
                    rawData?.let {
                        if (it.resultCount == 0) {
                            data.add(NotFoundItem(textProblem = getString(R.string.not_found)))
                        } else {
                            for (item in it.results) {
                                data.add(item)
                            }
                        }
                    }
//                    isSearchSubmitted = true
                    updateData()
                } else {
                    Log.i("RETROFIT_ERROR", "${response.errorBody()?.string()}")
                    data.clear()
                    data.add(
                        ErrorItem(
                            text = getString(R.string.not_connect_item_text),
                            textSub = getString(R.string.not_connect_item_text_sub)
                        )
                    )
//                    isSearchSubmitted = true
                    updateData()
                }
            }
        })
    }

    private fun updateData() {
        progressBar.visibility = View.INVISIBLE
        recycler.visibility = View.VISIBLE
        println(data)
        adapter.songs = data
        adapter.notifyDataSetChanged()
        this.currentFocus?.let {
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun renderHistory() {
        data.clear()
        val historyData = songHistory.getHistory().toMutableList()
        if (historyData.isNotEmpty()) {
            data.add(SongItemTitle(text = getString(R.string.title_history)))
            data.addAll(songHistory.getHistory().toMutableList())
            data.add(SongItemButton(text = getString(R.string.clean_history)))
        }
        updateData()
    }

    override fun clickUpdate() {
        getSongsITunes(inputTextSave)
    }

    override fun clickOnSong(item: SongItem) {
        Log.i("SEARCH_ACTIVITY", "Click on Song!!")
        if (isClickDebounce()) {
            songHistory.saveSong(item)
            val descSongIntent = Intent(this, DescriptionSongActivity::class.java)
            descSongIntent.putExtra(DescriptionSongActivity.TRACK_ID, item.trackId)
            startActivity(descSongIntent)
        }
    }

    private fun isClickDebounce(): Boolean {
        val currentState = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed(
                { isClickAllowed = true }, CLICK_DEBOUNCE_DELAY
            )
        }
        return currentState
    }

    override fun cleanHistory() {
        songHistory.cleanHistory()
        renderHistory()
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    companion object {
        private const val EDIT_TEXT = "EDIT_TEXT"
        private const val IS_SEARCH_SUBMITTED = "IS_SEARCH_SUBMITTED"
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}
