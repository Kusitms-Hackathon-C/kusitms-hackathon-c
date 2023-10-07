package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentReviewCompleteBinding

class ReviewCompleteFragment : Fragment() {

    lateinit var fragmentReviewCompleteBinding: FragmentReviewCompleteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReviewCompleteBinding = FragmentReviewCompleteBinding.inflate(inflater)

        fragmentReviewCompleteBinding.run {
            materialToolbar.run {
                title = "후기 등록하기"
            }
        }
        return fragmentReviewCompleteBinding.root
    }
}