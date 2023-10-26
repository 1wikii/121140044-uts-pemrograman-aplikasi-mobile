package com.example.uts_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.uts_project.databinding.ActivityMotionBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class MotionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMotionBinding

    private val collection = "loginstate"
    private val document = "pJ3LFv3ZxSb9aOH8U1Kv"
    private val db = Firebase.firestore
    private var isLoggedIn: String? = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val motionLogo = findViewById<ImageView>(R.id.imageView)
        motionLogo.alpha = 0f

        motionLogo.animate().setDuration(1000).alpha(1f).withEndAction{

            binding.button.setOnClickListener{
                val i = Intent( this, LoginActivity::class.java)
                startActivity(i)

            }

        }

    }


}


