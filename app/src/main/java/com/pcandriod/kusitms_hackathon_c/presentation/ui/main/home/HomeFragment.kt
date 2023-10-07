package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentHomeBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        return binding.root
    }
}