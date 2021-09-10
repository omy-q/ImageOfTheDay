package com.example.imageoftheday.superview.view

import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.example.imageoftheday.R
import com.example.imageoftheday.databinding.IncludeChipsBinding
import com.google.android.material.chip.Chip

class ChipsFragment : Fragment() {

    private var _binding : IncludeChipsBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() = ChipsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = IncludeChipsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chipGroup.setOnCheckedChangeListener{childGroup, position ->
            Toast.makeText(context, "Click$position", Toast.LENGTH_LONG).show()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.chipGroup.forEachIndexed { index, view ->
                    (view as Chip).typeface = Typeface.DEFAULT
                }
                (childGroup.getChildAt(position - 10) as Chip).typeface = resources.getFont(
                    R.font.a)
            }
        }
        binding.chipClose.setOnCloseIconClickListener{
            Toast.makeText(context, "Click on chipWithClose", Toast.LENGTH_SHORT).show()
        }
    }
}