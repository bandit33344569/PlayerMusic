package com.bro.playermusic.presentation.api

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bro.playermusic.databinding.FragmentApiBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApiFragment : Fragment() {

    private var _binding: FragmentApiBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val apiViewModel =
            ViewModelProvider(this).get(ApiViewModel::class.java)

        _binding = FragmentApiBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        apiViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}