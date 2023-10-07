package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.remote.response.MapStoreResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.response.SosList
import com.pcandriod.kusitms_hackathon_c.data.remote.response.StoreSosResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.service.MapService
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentStoreInfoOwnerBinding
import com.pcandriod.kusitms_hackathon_c.databinding.RowStoreInfoOwnerBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoreInfoOwnerFragment : Fragment() {

    lateinit var fragmentStoreInfoOwnerBinding: FragmentStoreInfoOwnerBinding
    lateinit var mainActivity : MainActivity

    var sosList = mutableListOf<SosList>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentStoreInfoOwnerBinding = FragmentStoreInfoOwnerBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentStoreInfoOwnerBinding.run {
            getStoreSos()

            val handler = Handler()
            handler.postDelayed({
                rvOwner.run {
                    adapter = RecyclerViewAdapter()

                    layoutManager = LinearLayoutManager(requireContext())
                }
            }, 1000) // 2000ms (2초) 후에 RecyclerView 생성

        }
        return fragmentStoreInfoOwnerBinding.root
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowStoreInfoOwnerBinding) :
            RecyclerView.ViewHolder(rowBinding.root) {

            val rowContent: TextView
            val rowDate: TextView

            init {
                rowContent = rowBinding.tvOwnerContent
                rowDate = rowBinding.tvDate
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowPostListBinding = RowStoreInfoOwnerBinding.inflate(layoutInflater)
            val viewHolder = ViewHolderClass(rowPostListBinding)

            rowPostListBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return viewHolder
        }

        override fun getItemCount(): Int {
            return sosList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowContent.text = sosList.get(position).content.toString()
            holder.rowDate.text = "2023.10.08."
        }
    }


    fun getStoreSos() {

        val api = ApiModule.getInstance().create(MapService::class.java)

        Log.d("##", "${mainActivity.storeId}")
        api.getStoreSos(mainActivity.storeId).enqueue(object : Callback<StoreSosResponse> {
            override fun onResponse(
                call: Call<StoreSosResponse>,
                response: Response<StoreSosResponse>
            ) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성공된 경우
                    var result: StoreSosResponse? = response.body()
                    Log.d("##", "onResponse 성공: " + result?.toString())

                    for(i in 0 until result!!.content.size) {
                        sosList.add(result.content.get(i))
                        Log.d("##", "sos : ${sosList}")
                    }

                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    var result: StoreSosResponse? = response.body()
                    val errorBody = response.errorBody()?.string() // 에러 응답 데이터를 문자열로 얻음
                    Log.d("##", "Error Response: $errorBody")
                    Log.d("##", "onResponse 실패")
                    Log.d("##", "onResponse 실패: " + response.code())
                }
            }

            override fun onFailure(call: Call<StoreSosResponse>, t: Throwable) {
                // 통신 실패
                Log.d("##", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}