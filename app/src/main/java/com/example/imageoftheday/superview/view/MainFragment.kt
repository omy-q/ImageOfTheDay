package com.example.imageoftheday.superview.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.imageoftheday.R
import com.example.imageoftheday.databinding.MainFragmentBinding
import com.example.imageoftheday.superview.viewmodel.AppState
import com.example.imageoftheday.superview.viewmodel.MainViewModel
import java.util.*

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            getData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOptions()
    }

    private fun initOptions() {
        binding.inputLayout.setEndIconOnClickListener{
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("https://en.wikipedia.org/wiki/" +
                        "${binding.inputEditText.text.toString()}")
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                TODO()
            }
            is AppState.Success -> {
                binding.imageView.load(appState.serverResponseData.url) {
                    error(R.drawable.ic_load_error_vector)
                }
            }
            AppState.Loading -> Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
        }
    }
}