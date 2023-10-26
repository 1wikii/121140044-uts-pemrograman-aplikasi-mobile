package com.example.uts_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.uts_project.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val collection = "loginstate"
    private val document = "pJ3LFv3ZxSb9aOH8U1Kv"
    private val key = "isLoggedIn"

    private val db = Firebase.firestore
    private val data = hashMapOf(
        key to "yes"
    )

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogin.setOnClickListener{
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()){

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{

                    if(it.isSuccessful){

                        // Mengirim data ke Firebase Firestore
                        db.collection(collection).document(document)
                            .set(data)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Cannot Access Firestore!", Toast.LENGTH_SHORT).show()
                            }

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
                    .addOnFailureListener{
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }

            }else{
                Toast.makeText(this, "Please complete the login!", Toast.LENGTH_SHORT).show()
            }

        }

        binding.buttonRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // show password when click
        val checkbox = findViewById<CheckBox>(R.id.login_show_password)
        val password = findViewById<EditText>(R.id.login_password)
        checkbox.setOnClickListener{
            if(checkbox.isChecked){
                password.inputType = 1
                password.setSelection(password.text.length)
            }else{
                password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                password.setSelection(password.text.length)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}