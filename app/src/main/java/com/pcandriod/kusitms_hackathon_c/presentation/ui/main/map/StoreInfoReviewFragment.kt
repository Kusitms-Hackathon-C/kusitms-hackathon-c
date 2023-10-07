package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ReivewList
import com.pcandriod.kusitms_hackathon_c.data.remote.response.ReviewResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.response.StoreSosResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.service.MapService
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentStoreInfoReviewBinding
import com.pcandriod.kusitms_hackathon_c.databinding.RowStoreInfoOwnerBinding
import com.pcandriod.kusitms_hackathon_c.databinding.RowStoreInfoReviewBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreInfoReviewFragment : Fragment() {

    lateinit var fragmentStoreInfoReviewBinding: FragmentStoreInfoReviewBinding
    lateinit var mainActivity: MainActivity

    var reviewList = mutableListOf<ReivewList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentStoreInfoReviewBinding = FragmentStoreInfoReviewBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentStoreInfoReviewBinding.run {

            getStoreReview()

            val handler = Handler()
            handler.postDelayed({
                rvReview.run {
                    adapter = RecyclerViewAdapter()

                    layoutManager = LinearLayoutManager(requireContext())
                }
            }, 1000) // 2000ms (2초) 후에 RecyclerView 생성
        }
        return fragmentStoreInfoReviewBinding.root
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowStoreInfoReviewBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            val rowName: TextView
            val rowDate: TextView
            val rowContent: TextView

            init {
                rowName = rowBinding.tvName
                rowDate = rowBinding.tvReviewDate
                rowContent = rowBinding.tvReview
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowPostListBinding = RowStoreInfoReviewBinding.inflate(layoutInflater)
            val viewHolder = ViewHolderClass(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return reviewList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowName.text = "${reviewList.get(position).username}"
            holder.rowContent.text = "${reviewList.get(position).content}"
            holder.rowDate.text = "2023.10.08."
        }
    }

    fun getStoreReview() {

        val api = ApiModule.getInstance().create(MapService::class.java)

        Log.d("##", "${mainActivity.storeId}")
        api.getStoreReview(mainActivity.storeId).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(
                call: Call<ReviewResponse>,
                response: Response<ReviewResponse>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: ReviewResponse? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    for(i in 0 until result!!.numberOfElements) {
                        reviewList.add(result.content.get(i))
                        Log.d("##", "sos : ${reviewList}")
                    }

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: ReviewResponse? = response.body()
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}