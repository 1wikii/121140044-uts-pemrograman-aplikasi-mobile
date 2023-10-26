package com.example.uts_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.uts_project.databinding.ActivityMotionBinding
import com.example.uts_project.databinding.ActivitySplashBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class SplashActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySplashBinding
    private val binding get() = _binding!!

    private val collection = "loginstate"
    private val document = "pJ3LFv3ZxSb9aOH8U1Kv"
    private val db = Firebase.firestore
    private var isLoggedIn: String? = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data dari Firebase Firestore
        db.collection(collection).document(document)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    isLoggedIn = document.getString("isLoggedIn")

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Cannot Access Firestore!", Toast.LENGTH_SHORT).show()
            }

        binding.splashLogo.alpha = 0f

        binding.splashLogo.animate().setDuration(3000).alpha(1f).withEndAction{
            if(isLoggedIn != "no"){
                val i = Intent( this, MainActivity::class.java)
                startActivity(i)
            }else{
                val i = Intent( this, MotionActivity::class.java)
                startActivity(i)
            }
        }

    }


}


