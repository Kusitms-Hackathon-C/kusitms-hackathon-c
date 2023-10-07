package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentStoreInfoReviewBinding
import com.pcandriod.kusitms_hackathon_c.databinding.RowStoreInfoOwnerBinding
import com.pcandriod.kusitms_hackathon_c.databinding.RowStoreInfoReviewBinding

class StoreInfoReviewFragment : Fragment() {

    lateinit var fragmentStoreInfoReviewBinding: FragmentStoreInfoReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentStoreInfoReviewBinding = FragmentStoreInfoReviewBinding.inflate(inflater)

        fragmentStoreInfoReviewBinding.run {
            rvReview.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(requireContext())
            }
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
            return 3
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        }
    }
}