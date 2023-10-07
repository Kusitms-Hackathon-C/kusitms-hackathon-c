package com.pcandriod.kusitms_hackathon_c.presentation.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pcandriod.kusitms_hackathon_c.R
import com.pcandriod.kusitms_hackathon_c.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}