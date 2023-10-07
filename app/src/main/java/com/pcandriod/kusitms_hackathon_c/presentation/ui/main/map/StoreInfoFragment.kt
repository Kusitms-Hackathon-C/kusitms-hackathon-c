package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.data.module.api.ApiModule
import com.pcandriod.kusitms_hackathon_c.data.remote.response.MapStoreResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.response.StoreSosResponse
import com.pcandriod.kusitms_hackathon_c.data.remote.service.MapService
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentStoreInfoBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreInfoFragment : Fragment() {

    lateinit var fragmentStoreInfoBinding: FragmentStoreInfoBinding
    lateinit var mainActivity: MainActivity

    var storeId = 0

    val fragmentList = mutableListOf<Fragment>()

    // 탭에 표시할 이름
    val tabName = arrayOf(
        "지킴이 후기","사장님 이야기"
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentStoreInfoBinding = FragmentStoreInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentStoreInfoBinding.run {

            textView2.text = "${mainActivity.storeReviewCount}명이 이 가게를 지키고 싶어 해요!"
            imageView.setImageResource(R.drawable.step0)
            if(mainActivity.storeReviewCount == 1) {
                imageView.setImageResource(R.drawable.step1)
            } else if(mainActivity.storeReviewCount == 2) {
                imageView.setImageResource(R.drawable.step2)
            } else if(mainActivity.storeReviewCount == 3) {
                imageView.setImageResource(R.drawable.step3)
            } else if(mainActivity.storeReviewCount == 4) {
                imageView.setImageResource(R.drawable.step4)
            } else if(mainActivity.storeReviewCount == 5) {
                imageView.setImageResource(R.drawable.step5)
            }

            materialToolbar.run {
                title = "${mainActivity.storeName}"
            }

            fragmentList.clear()
            fragmentList.add(StoreInfoReviewFragment())
            fragmentList.add(StoreInfoOwnerFragment())

            fragmentStoreInfoBinding.run {
                pagerTab.setUserInputEnabled(false)
                pagerTab.adapter = TabAdapterClass(requireActivity())

                // 탭 구성
                val tabLayoutMediator =
                    TabLayoutMediator(tab, pagerTab) { tab: TabLayout.Tab, i: Int ->
                        tab.text = tabName[i]
                    }
                tabLayoutMediator.attach()

            }


            return fragmentStoreInfoBinding.root
        }
    }

    override fun onResume() {
        super.onResume()
        fragmentList.clear()
        fragmentList.add(StoreInfoReviewFragment())
        fragmentList.add(StoreInfoOwnerFragment())
        fragmentStoreInfoBinding.pagerTab.requestLayout()
    }


    // adapter 클래스
    inner class TabAdapterClass(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            Log.d("##","fragmentList size : ${fragmentList.size}")
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

    }
}