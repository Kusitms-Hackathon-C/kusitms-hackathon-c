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
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.write.WriteCustomerFragment
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

        binding.btnStoreNews.setOnClickListener {
            binding.btnStoreNews.setBackgroundResource(R.drawable.bg_button_true)
            binding.btnStoreNews.setTextColor(resources.getColor(R.color.white))
            binding.btnSos.setBackgroundResource(R.drawable.bg_button_false)
            binding.btnSos.setTextColor(resources.getColor(R.color.black))
            binding.btnMostLike.setBackgroundResource(R.drawable.bg_button_false)
            binding.btnMostLike.setTextColor(resources.getColor(R.color.black))
            apiStart()
        }

        binding.btnSos.setOnClickListener {
            binding.btnStoreNews.setBackgroundResource(R.drawable.bg_button_false)
            binding.btnStoreNews.setTextColor(resources.getColor(R.color.black))
            binding.btnSos.setBackgroundResource(R.drawable.bg_button_true)
            binding.btnSos.setTextColor(resources.getColor(R.color.white))
            binding.btnMostLike.setBackgroundResource(R.drawable.bg_button_false)
            binding.btnMostLike.setTextColor(resources.getColor(R.color.black))
            sosAPI()
        }

        binding.btnMostLike.setOnClickListener {
            binding.btnStoreNews.setBackgroundResource(R.drawable.bg_button_false)
            binding.btnStoreNews.setTextColor(resources.getColor(R.color.black))
            binding.btnSos.setBackgroundResource(R.drawable.bg_button_false)
            binding.btnSos.setTextColor(resources.getColor(R.color.black))
            binding.btnMostLike.setBackgroundResource(R.drawable.bg_button_true)
            binding.btnMostLike.setTextColor(resources.getColor(R.color.white))
            likeAPI()
        }
    }

    private fun initView() {
        mRecyclerView = binding.rvHomePost
        mRecyclerView.adapter = PostAdapter(itemList)
    }

    private fun goToWrite() {
        binding.fabAddPost.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fv_main, WriteCustomerFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun apiStart() {
        itemList.clear()

        val api = ApiModule.getInstance().create(HomeService::class.java)
        api.getPostCategory()
            .enqueue(object: Callback<ResponsePost> {
                override fun onResponse(
                    call: Call<ResponsePost>,
                    response: Response<ResponsePost>
                ) {
                    var postList: List<ResponsePost.Content>?
                    Log.d("HomeFragment", "API 성공 ${response.body()}")
                    if (response.isSuccessful) {
                        postList = response.body()?.content?.toList()

                        postList?.forEach {
                            val postItem = PostItem(it.title, it.content)
                            itemList.add(postItem)
                        }

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

    private fun sosAPI() {
        itemList.clear()

        val api = ApiModule.getInstance().create(HomeService::class.java)
        api.getPostCategorySOS()
            .enqueue(object: Callback<ResponsePost> {
                override fun onResponse(
                    call: Call<ResponsePost>,
                    response: Response<ResponsePost>
                ) {
                    var postList: List<ResponsePost.Content>?
                    Log.d("HomeFragment", "API 성공 ${response.body()}")
                    if (response.isSuccessful) {
                        postList = response.body()?.content?.toList()

                        postList?.forEach {
                            val postItem = PostItem(it.title, it.content)
                            itemList.add(postItem)
                        }

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

    private fun likeAPI() {
        itemList.clear()

        val api = ApiModule.getInstance().create(HomeService::class.java)
        api.getPostCategoryLike()
            .enqueue(object: Callback<ResponsePost> {
                override fun onResponse(
                    call: Call<ResponsePost>,
                    response: Response<ResponsePost>
                ) {
                    var postList: List<ResponsePost.Content>?
                    Log.d("HomeFragment", "API 성공 ${response.body()}")
                    if (response.isSuccessful) {
                        postList = response.body()?.content?.toList()

                        postList?.forEach {
                            val postItem = PostItem(it.title, it.content)
                            itemList.add(postItem)
                        }

                        val postAdapter = PostAdapter(itemList)
                        binding.rvHomePost.adapter = postAdapter
                        postAdapter.notifyDataSetChanged()

                        binding.btnStoreNews.setBackgroundResource(R.drawable.bg_button_false)
                        binding.btnSos.setBackgroundResource(R.drawable.bg_button_false)
                        binding.btnMostLike.setBackgroundResource(R.drawable.bg_button_true)
                    }
                }

                override fun onFailure(call: Call<ResponsePost>, t: Throwable) {
                    Log.e("HomeFragment", "API 실패 ${t}")
                }

            })
    }
}