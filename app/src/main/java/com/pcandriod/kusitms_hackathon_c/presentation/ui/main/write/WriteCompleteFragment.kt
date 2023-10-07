package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentWriteCompleteBinding
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentWriteCustomerBinding


class WriteCompleteFragment : Fragment() {

    lateinit var fragmentWriteCompleteBinding: FragmentWriteCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentWriteCompleteBinding = FragmentWriteCompleteBinding.inflate(inflater)

        fragmentWriteCompleteBinding.run {
            materialToolbar.run {
                title = "글쓰기"
            }
        }
        return fragmentWriteCompleteBinding.root
    }
}