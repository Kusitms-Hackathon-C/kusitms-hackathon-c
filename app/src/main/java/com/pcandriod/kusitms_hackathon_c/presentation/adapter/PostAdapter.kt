package com.pcandriod.kusitms_hackathon_c.presentation.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pcandriod.kusitms_hackathon_c.data.data.PostItem
import com.pcandriod.kusitms_hackathon_c.databinding.ItemHomePostBinding
import com.pcandriod.kusitms_hackathon_c.presentation.ui.post.PostActivity

class PostAdapter(private val itemPost: ArrayList<PostItem>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var onItemClickListener: ((Int) -> Unit)? = null
    private var context: Context? = null

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        onItemClickListener = listener
    }

    private var articleIds: ArrayList<Int> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        context = parent.context
        val binding =
            ItemHomePostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val itemList = itemPost?.get(position)
        holder.title.text = itemList?.title
        holder.content.text = itemList?.content
    }


    override fun getItemCount() = itemPost.size

    inner class PostViewHolder(val binding: ItemHomePostBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val content = binding.tvContent
        val id: Int? = 0

        init {
            binding.root.setOnClickListener {
                val context = it.context
                val intent = Intent(context, PostActivity::class.java)
                intent.putExtra("제목", binding.tvTitle.text)
                intent.putExtra("내용", binding.tvContent.text)
                intent.putExtra("id", id)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }



}