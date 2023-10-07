package com.pcandriod.kusitms_hackathon_c.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pcandriod.kusitms_hackathon_c.data.data.PostItem
import com.pcandriod.kusitms_hackathon_c.databinding.ItemHomePostBinding

class PostAdapter(private val itemPost: ArrayList<PostItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            ItemHomePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.setItem(itemPost[position].title, itemPost[position].content)
        holder.binding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition

            Log.d("클릭아벤트", "아이템 클릭 $position")
            onItemClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = itemPost.size

    class PostViewHolder(val binding: ItemHomePostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(title: String, content: String) {
            binding.tvTitle.text = title
            binding.tvContent.text = content
        }
    }
}