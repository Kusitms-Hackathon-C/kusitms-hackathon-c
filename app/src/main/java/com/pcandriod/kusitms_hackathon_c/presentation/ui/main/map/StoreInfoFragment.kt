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
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentStoreInfoBinding

class StoreInfoFragment : Fragment() {

    lateinit var fragmentStoreInfoBinding: FragmentStoreInfoBinding

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

        fragmentStoreInfoBinding.run {

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