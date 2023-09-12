package com.delirium.playlistmaker.media.ui.fragment.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentPlayListMediaBinding
import com.delirium.playlistmaker.media.ui.models.PlayListState
import com.delirium.playlistmaker.media.ui.viewmodel.PlayListMediaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListMediaFragment : Fragment() {

    private lateinit var binding: FragmentPlayListMediaBinding
    private val viewModel by viewModel<PlayListMediaViewModel>()

    private val adapter by lazy {
        PlayListAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init()
        binding.buttonCreateNewPlaylist.setOnClickListener {
            findNavController().navigate(
                R.id.action_mediaFragment_to_mediaCreateFragment
            )
        }

        viewModel.getPlayListLiveData().observe(viewLifecycleOwner) { state ->
            when(state) {
                PlayListState.Empty -> {
                    changeVisibility(true)
                }
                is PlayListState.Content -> {
                    changeVisibility(false)
                    adapter.data = state.data
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.playlistRecycler.layoutManager = GridLayoutManager(requireContext(), COUNT_GRID)
        binding.playlistRecycler.adapter = adapter
    }

    private fun changeVisibility(isEmpty: Boolean) {
        if(isEmpty) {
            binding.playlistRecycler.visibility = View.GONE
            binding.imageProblemItem.visibility = View.VISIBLE
            binding.textProblemItem.visibility = View.VISIBLE
        } else {
            binding.playlistRecycler.visibility = View.VISIBLE
            binding.imageProblemItem.visibility = View.GONE
            binding.textProblemItem.visibility = View.GONE
        }
    }
    companion object {
        fun newInstance() = PlayListMediaFragment()

        const val COUNT_GRID = 2
    }
}
