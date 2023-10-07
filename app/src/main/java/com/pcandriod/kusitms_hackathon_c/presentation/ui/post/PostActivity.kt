package com.pcandriod.kusitms_hackathon_c.presentation.ui.post

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ResponsePostDetail
import com.pcandriod.kusitms_hackathon_c.data.remote.service.PostService
import com.pcandriod.kusitms_hackathon_c.databinding.ActivityPostBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private var id = 0
    private var title = ""
    private var content = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initArticle()
        getPostDetail()

        binding.ibtnClose.setOnClickListener {
            finish()
        }
    }

    private fun initArticle() {
        val articleIntent: Intent = intent
        title = articleIntent.getStringExtra("title").toString()
        content = articleIntent.getStringExtra("content").toString()
        id = articleIntent.getIntExtra("id", 0)
    }

    private fun getPostDetail() {


        val api = ApiModule.getInstance().create(PostService::class.java)
        api.getPostDetail(id)
            .enqueue(object : Callback<ResponsePostDetail> {
                override fun onResponse(
                    call: Call<ResponsePostDetail>,
                    response: Response<ResponsePostDetail>
                ) {
                    if (response.isSuccessful) {
                        Log.d("HomeFragment", "API 성공 ${response.body()}")

                    }
                }

                override fun onFailure(call: Call<ResponsePostDetail>, t: Throwable) {
                    Log.e("HomeFragment", "API 실패 ${t}")
                }

            })
    }
}