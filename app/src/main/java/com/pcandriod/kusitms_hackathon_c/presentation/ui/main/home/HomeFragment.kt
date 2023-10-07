package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.pcandriod.kusitms_hackathon_c.data.data.PostItem
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentHomeBinding
import com.pcandriod.kusitms_hackathon_c.presentation.adapter.PostAdapter
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity
    private var itemList = ArrayList<PostItem>()

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        addDummyData()
    }

    private fun initView() {
        mRecyclerView = binding.rvHomePost
        mRecyclerView.adapter = PostAdapter(itemList)
    }

    private fun addDummyData() {
        for (i in 1..10) {
            val postItem = PostItem(
                title = "더미 제목 $i",
                content = "더미 내용 $i",
                time = 13,
                comment = 0,
            )
            itemList.add(postItem)
        }

        (mRecyclerView.adapter as PostAdapter).notifyDataSetChanged()
    }
}