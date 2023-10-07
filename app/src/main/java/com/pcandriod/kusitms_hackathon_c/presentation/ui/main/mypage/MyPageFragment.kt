package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentMypageBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity

class MyPageFragment : Fragment() {
    lateinit var binding: FragmentMypageBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        return binding.root
    }
}