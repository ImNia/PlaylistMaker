package com.delirium.playlistmaker.search.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.ActivitySearchBinding
import com.delirium.playlistmaker.search.data.models.ModelForAdapter
import com.delirium.playlistmaker.search.data.models.SongItem
import com.delirium.playlistmaker.search.data.models.SongItemButton
import com.delirium.playlistmaker.search.data.models.SongItemTitle
import com.delirium.playlistmaker.search.data.models.SongListItem
import com.delirium.playlistmaker.search.ui.models.SearchState
import com.delirium.playlistmaker.search.ui.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity(), ClickListener {
    private lateinit var binding: ActivitySearchBinding
    private var inputTextSave: String = ""

    private val adapter = AdapterModel(this)

    private lateinit var viewModel: SearchViewModel

    private var isSearchSubmitted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBarSearch)

        viewModel =  ViewModelProvider(
            this,
            SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]

        viewModel.getSongIntentLiveData().observe(this) { intent ->
            openSongDescription(intent)
        }

        viewModel.getSearchStateLiveData().observe(this) { searchState ->
            when(searchState) {
                is SearchState.Content -> {
                    changeContentVisibility(false)
                    updateData(listOf(searchState.data))
                }
                is SearchState.Error -> {
                    changeContentVisibility(false)
                    updateData(listOf(searchState.data))
                }
                is SearchState.Empty -> {
                    changeContentVisibility(false)
                    updateData(listOf(searchState.data))
                }
                is SearchState.Loading -> {
                    changeContentVisibility(true)
                }
            }
        }

        viewModel.getHistoryLiveData().observe(this) { history ->
            renderHistory(history.asList())
        }

        binding.recyclerSongs.layoutManager = LinearLayoutManager(this)
        binding.recyclerSongs.adapter = adapter

        binding.clearSearch.setOnClickListener { it ->
            binding.editSearch.text.clear()
            it.visibility = View.INVISIBLE

            this.currentFocus?.let {
                val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(it.windowToken, 0)
            }
            viewModel.getHistory()
            isSearchSubmitted = false
        }

        binding.editSearch.addTextChangedListener(createTextWatcher())

        binding.editSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.editSearch.text.isEmpty()) {
                viewModel.getHistory()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val inputString = savedInstanceState.getString(EDIT_TEXT)
        isSearchSubmitted = savedInstanceState.getBoolean(IS_SEARCH_SUBMITTED)
        binding.editSearch.setText(inputString)
        if (isSearchSubmitted) viewModel.getSongOnInputText(inputTextSave)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT, inputTextSave)
        outState.putBoolean(IS_SEARCH_SUBMITTED, isSearchSubmitted)
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.clearSearch.visibility = View.VISIBLE
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            inputTextSave = binding.editSearch.text.toString()
            if (binding.editSearch.hasFocus() && binding.editSearch.text.isEmpty()) {
                viewModel.getHistory()
            } else {
                viewModel.getSongOnInputText(inputTextSave)
            }
            isSearchSubmitted = true
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun updateData(data: List<ModelForAdapter>) {
        if (data.isNotEmpty() && data.first() is SongListItem) {
            adapter.songs = (data.first() as SongListItem).songs
        } else {
            adapter.songs = data
        }
        adapter.notifyDataSetChanged()
        this.currentFocus?.let {
            val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun renderHistory(history: List<SongItem>) {
        val data = mutableListOf<ModelForAdapter>()
        if (history.isNotEmpty()) {
            data.add(SongItemTitle(text = getString(R.string.title_history)))
            data.addAll(history)
            data.add(SongItemButton(text = getString(R.string.clean_history)))
        }
        updateData(data)
    }

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.recyclerSongs.isVisible = !loading
    }

    private fun openSongDescription(intent: Intent) {
        startActivity(intent)
    }
    override fun clickUpdate() {
        viewModel.getSongOnInputText(inputTextSave)
    }

    override fun clickOnSong(item: SongItem) {
        viewModel.openSongOnId(item)
    }

    override fun cleanHistory() {
        viewModel.clearHistory()
    }

    companion object {
        private const val EDIT_TEXT = "EDIT_TEXT"
        private const val IS_SEARCH_SUBMITTED = "IS_SEARCH_SUBMITTED"
    }
}
