package com.bro.playermusic.presentation.local

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bro.playermusic.databinding.FragmentLocalBinding
import com.bro.playermusic.presentation.adapter.TrackListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocalFragment : Fragment() {

    private var _binding: FragmentLocalBinding? = null
    private val adapter = TrackListAdapter()
    private val binding get() = _binding!!
    private val viewModel: LocalViewModel by viewModels()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            observeAudioFiles()
        } else {
            showPermissionDeniedDialog()
        }
    }

    private fun checkAndRequestPermissions() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_AUDIO
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                observeAudioFiles()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                showPermissionRationaleDialog(permission)
            }

            else -> {
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private fun observeAudioFiles() {
        lifecycleScope.launch {
            viewModel.tracks.collect {
                adapter.data = it
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLocalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupRecyclerView()
        checkAndRequestPermissions()
        setupSearchView()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.trackListRecyclerView.adapter = adapter
        binding.trackListRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun showPermissionRationaleDialog(permission: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Необходимо разрешение")
            .setMessage("Приложению нужно разрешение для доступа к вашей медиатеке, чтобы воспроизводить музыку.")
            .setPositiveButton("ОК") { _, _ ->
                requestPermissionLauncher.launch(permission)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Разрешение отклонено")
            .setMessage("Без этого разрешения приложение не может отображать вашу музыку.  Перейдите в настройки приложения, чтобы предоставить разрешение вручную.")
            .setPositiveButton("Настройки") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivity(intent)
            }
            .setNegativeButton("Закрыть", null)
            .show()

    }

    private fun setupSearchView() {
        binding.localSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.getFilteredTracks(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.getFilteredTracks(it)
                }
                return true
            }
        })
    }

}