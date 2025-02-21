package com.bro.playermusic.presentation.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bro.playermusic.databinding.FragmentApiBinding
import com.bro.playermusic.presentation.adapter.TrackListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null
    val viewModel: ApiViewModel by viewModels()
    private val binding get() = _binding!!
    private val adapter = TrackListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentApiBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        observeAudioFiles()
        setupSearchView()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeAudioFiles() {
        lifecycleScope.launch {
            viewModel.tracks.collect {
                adapter.data = it
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apiTrackListRecyclerView.adapter = adapter
        binding.apiTrackListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun setupSearchView() {
        binding.apiSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchTracks(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isBlank())
                        viewModel.getChartTracks()
                    else
                        viewModel.searchTracks(it)
                }
                return true
            }
        })
    }
}