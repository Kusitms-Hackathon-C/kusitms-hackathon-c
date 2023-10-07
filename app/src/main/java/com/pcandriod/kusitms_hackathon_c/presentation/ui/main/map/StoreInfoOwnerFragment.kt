package com.pcandriod.kusitms_hackathon_c.presentation.ui.main.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.databinding.FragmentStoreInfoOwnerBinding
import com.pcandriod.kusitms_hackathon_c.databinding.RowStoreInfoOwnerBinding


class StoreInfoOwnerFragment : Fragment() {

    lateinit var fragmentStoreInfoOwnerBinding: FragmentStoreInfoOwnerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentStoreInfoOwnerBinding = FragmentStoreInfoOwnerBinding.inflate(inflater)

        fragmentStoreInfoOwnerBinding.run {
            rvOwner.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(requireContext())
            }
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
            return 3
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

        }
    }
}