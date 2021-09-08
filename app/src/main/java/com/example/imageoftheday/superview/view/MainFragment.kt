package com.example.imageoftheday.superview.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.imageoftheday.R
import com.example.imageoftheday.databinding.MainFragmentBinding
import com.example.imageoftheday.superview.viewmodel.AppState
import com.example.imageoftheday.superview.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by
        lazy{ ViewModelProvider(this).get(MainViewModel::class.java) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

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
        setBottomAppBar()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(viewModel) {
            getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            getData()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.app_bar_fav -> {
                Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show()
            }
            R.id.app_bar_settings -> {
                Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOptions()
        initBottomSheet(binding.includeLayout.bottomSheetContainer)
    }

    private fun setBottomAppBar() {
        (context as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }

    private fun initBottomSheet(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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