package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.data.PostItem
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponsePost
import com.pcandriod.kusitms_hackathon_c.data.remote.service.HomeService
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentHomeBinding
import com.pcandriod.kusitms_hackathon_c.presentation.adapter.PostAdapter
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.write.WriteOwnerFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        apiStart()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        goToWrite()

    }

    private fun initView() {
        mRecyclerView = binding.rvHomePost
        mRecyclerView.adapter = PostAdapter(itemList)
    }

    private fun goToWrite() {
        binding.fabAddPost.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fv_main, WriteOwnerFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun apiStart() {
        val api = ApiModule.getInstance().create(HomeService::class.java)
        api.getPostCategory()
            .enqueue(object: Callback<ResponsePost> {
                override fun onResponse(
                    call: Call<ResponsePost>,
                    response: Response<ResponsePost>
                ) {
                    Log.d("HomeFragment", "API 성공 ${response.body()}")
                    if (response.isSuccessful) {
                        // API 요청이 성공하면 데이터를 가져옵니다.
                        val postList = response.body()?.content?.toList()

                        postList?.forEach {
                            val postItem = PostItem(it.title, it.content)
                            itemList.add(postItem)
                        }

                        // itemList를 사용하여 리사이클러뷰 어댑터를 업데이트합니다.
                        val postAdapter = PostAdapter(itemList)
                        binding.rvHomePost.adapter = postAdapter
                        postAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<ResponsePost>, t: Throwable) {
                    Log.e("HomeFragment", "API 실패 ${t}")
                }

            })
    }
}