package com.delirium.playlistmaker.search

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.SettingPreferences
import com.delirium.playlistmaker.search.itunes.ITunesSetting
import com.delirium.playlistmaker.search.itunes.model.*
import com.delirium.playlistmaker.search.songslist.AdapterSongs
import com.delirium.playlistmaker.search.songslist.ClickListener
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val toolbar = findViewById<Toolbar>(R.id.toolBarSearch)
        setSupportActionBar(toolbar)

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
            history()
        }

        editSearch = findViewById(R.id.edit_search)
        editSearch.addTextChangedListener(createTextWatcher())
        editSearch.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                getSongsITunes(inputTextSave)
                true
            } else {
                false
            }
        }

        history()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputString = savedInstanceState.getString(EDIT_TEXT)
        editSearch.setText(inputString)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT, inputTextSave)
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            crossForDelete.visibility = View.VISIBLE
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            inputTextSave = editSearch.text.toString()
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
                updateData()
            }

            override fun onResponse(
                call: Call<DataITunes>,
                response: Response<DataITunes>
            ) {
                if(response.isSuccessful) {
                    data.clear()
                    val rawData = response.body()
                    rawData?.let {
                        if (it.resultCount == 0) {
                            data.add(
                                NotFoundItem(
                                textProblem = getString(R.string.not_found)
                            )
                            )
                        } else {
                            for (item in it.results) {
                                data.add(item)
                            }
                        }
                    }
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
                    updateData()
                }
            }
        })
    }

    private fun updateData() {
        adapter.songs = data
        adapter.notifyDataSetChanged()
        this.currentFocus?.let {
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun history() {
        data.clear()
        data = SongHistory.getHistory(getSharedPreferences(SettingPreferences.FINDING_SONG.name, MODE_PRIVATE)).toMutableList()
        updateData()
    }
    companion object {
        private const val EDIT_TEXT = "EDIT_TEXT"
    }

    override fun clickUpdate() {
        getSongsITunes(inputTextSave)
    }

    override fun clickOnSong(item: SongItem) {
        Log.i("SEARCH_ACTIVITY", "Click on Song!!")
        SongHistory.saveSong(getSharedPreferences(SettingPreferences.FINDING_SONG.name, MODE_PRIVATE), item)
    }
}