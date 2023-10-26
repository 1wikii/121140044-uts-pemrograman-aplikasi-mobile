package com.example.uts_project.ui.profile

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uts_project.LoginActivity
import com.example.uts_project.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Locale

class ProfileFragment : Fragment() {

    private val db = Firebase.firestore
    private lateinit var backgroundColor: TypedArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileUser = view.findViewById<TextView>(R.id.profile_user)
        val username = view.findViewById<TextView>(R.id.profile_username)
        val githubUsername = view.findViewById<TextView>(R.id.profile_githubusername)
        val nim = view.findViewById<TextView>(R.id.profile_nim)
        val email = view.findViewById<TextView>(R.id.profile_email)
        val logoutBtn = view.findViewById<Button>(R.id.profile_logout)

        db.collection("users").document("JESxrqcBkzANTw2NbdBg").get()
            .addOnCompleteListener{ doc ->
                if(doc != null){

                    getBackgroundColor()
                    val charProfileUser = doc.result.get("username").toString()

                    profileUser.text = charProfileUser[0].uppercase(Locale.ROOT)
                    profileUser.setBackgroundResource(backgroundColor.getResourceId(doc.result.get("bgColor").toString().toInt(),0))

                    username.text = doc.result.get("username").toString()
                    githubUsername.text = doc.result.get("githubusername").toString()
                    nim.text = doc.result.get("nim").toString()
                    email.text = doc.result.get("email").toString()

                }else{
                    Toast.makeText(context, "User Data Not Found!", Toast.LENGTH_SHORT).show()
                }
            }

        val field = "isLoggedIn"
        val loginData = hashMapOf(      // untuk mengset bahwa user lagout jadi di set ke 'no'
            field to "no"
        )

        logoutBtn.setOnClickListener{
            db.collection("loginstate").document("pJ3LFv3ZxSb9aOH8U1Kv")
                .set(loginData)
                .addOnFailureListener{
                    Toast.makeText(context, "Error Database!", Toast.LENGTH_SHORT).show()
                }

            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getBackgroundColor(){
        backgroundColor = resources.obtainTypedArray(R.array.integer_profile_user_color)
    }

}