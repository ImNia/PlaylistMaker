package com.delirium.playlistmaker.search.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentSearchBinding
import com.delirium.playlistmaker.player.ui.activity.TrackActivity
import com.delirium.playlistmaker.search.domain.model.ErrorItem
import com.delirium.playlistmaker.search.domain.model.ModelForAdapter
import com.delirium.playlistmaker.search.domain.model.NotFoundItem
import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.search.domain.model.SongItemButton
import com.delirium.playlistmaker.search.domain.model.SongItemTitle
import com.delirium.playlistmaker.search.domain.model.SongListItem
import com.delirium.playlistmaker.search.ui.models.SearchState
import com.delirium.playlistmaker.search.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), ClickListener {
    private lateinit var binding: FragmentSearchBinding
    private val adapter = AdapterModel(this)

    private val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickUpdate()

        viewModel.getOpenPlayerLiveData().observe(viewLifecycleOwner) { trackId ->
            openSongDescription(trackId)
        }

        viewModel.getSearchStateLiveData().observe(viewLifecycleOwner) { searchState ->
            when (searchState) {
                is SearchState.Content -> {
                    changeContentVisibility(false)
                    updateData(listOf(searchState.data))
                }

                is SearchState.Error -> {
                    changeContentVisibility(false)
                    updateData(
                        listOf(
                            ErrorItem(
                                res = R.drawable.not_connect_search,
                                text = getString(R.string.not_connect_item_text),
                                textSub = getString(R.string.not_connect_item_text_sub),
                            )
                        )
                    )
                }

                is SearchState.Empty -> {
                    changeContentVisibility(false)
                    updateData(
                        listOf(
                            NotFoundItem(
                                res = R.drawable.not_search,
                                textProblem = getString(R.string.not_found),
                            )
                        )
                    )
                }

                is SearchState.Loading -> {
                    changeContentVisibility(true)
                }

                is SearchState.History -> {
                    renderHistory(searchState.data)
                }
            }
        }

        binding.recyclerSongs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSongs.adapter = adapter

        binding.clearSearch.setOnClickListener { it ->
            binding.editSearch.text.clear()
            it.visibility = View.INVISIBLE
            hideKeyboard()
            viewModel.updateInputText(binding.editSearch.text.toString())
        }

        binding.editSearch.addTextChangedListener(createTextWatcher())

        binding.editSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && binding.editSearch.text.isEmpty()) {
                viewModel.updateInputText(binding.editSearch.text.toString())
            }
        }
    }

    private fun createTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (binding.editSearch.text.isNotEmpty())
                binding.clearSearch.visibility = View.VISIBLE
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (binding.editSearch.text.isNotEmpty())
                binding.clearSearch.visibility = View.VISIBLE
            else
                binding.clearSearch.visibility = View.INVISIBLE

            if (binding.editSearch.hasFocus() && binding.editSearch.text.isEmpty()) {
                viewModel.updateInputText("")
            } else if (binding.editSearch.text.isNotEmpty()) {
                viewModel.updateInputText(binding.editSearch.text.toString())
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    private fun updateData(data: List<ModelForAdapter>, hideKeyboard: Boolean = true) {
        if (data.isNotEmpty() && data.first() is SongListItem) {
            adapter.songs = (data.first() as SongListItem).songs
        } else {
            adapter.songs = data
        }
        adapter.notifyDataSetChanged()
        if (hideKeyboard) hideKeyboard()
    }

    private fun hideKeyboard() {
        if (binding.editSearch.isFocused) {
            val keyboard =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.hideSoftInputFromWindow(binding.editSearch.applicationWindowToken, 0)
        }
    }

    private fun renderHistory(history: List<SongItem>) {
        val data = mutableListOf<ModelForAdapter>()
        if (history.isNotEmpty()) {
            data.add(SongItemTitle(text = getString(R.string.title_history)))
            data.addAll(history)
            data.add(SongItemButton(text = getString(R.string.clean_history)))
        }
        updateData(data, false)
    }

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading
        binding.recyclerSongs.isVisible = !loading
    }

    private fun openSongDescription(trackId: String) {
        val descSongIntent = Intent(requireContext(), TrackActivity::class.java)
        descSongIntent.putExtra(TRACK_ID, trackId)
        startActivity(descSongIntent)
    }

    override fun clickUpdate() {
        viewModel.updateInputText(binding.editSearch.text.toString())
    }

    override fun clickOnSong(item: SongItem) {
        viewModel.openSongOnId(item)
    }

    override fun cleanHistory() {
        viewModel.clearHistory()
    }

    companion object {
        private const val TRACK_ID = "TRACK_ID"
    }
}
