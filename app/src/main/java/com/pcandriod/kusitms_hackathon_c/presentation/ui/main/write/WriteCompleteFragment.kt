package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentWriteCompleteBinding
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentWriteCustomerBinding
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentMypageBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.home.HomeFragment
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WriteCompleteFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var fragmentWriteCompleteBinding: FragmentWriteCompleteBinding
    lateinit var binding: FragmentWriteCompleteBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteCompleteBinding.inflate(inflater, container, false)
        return binding.root


        fragmentWriteCompleteBinding = FragmentWriteCompleteBinding.inflate(inflater)

        fragmentWriteCompleteBinding.run {
            materialToolbar.run {
                title = "글쓰기"
            }
        }
        return fragmentWriteCompleteBinding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fv_main, HomeFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

}