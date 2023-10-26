package com.example.uts_project

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.uts_project.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.firestore
import kotlin.random.Random

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var backgroundColor: TypedArray

    private val collection = "users"
    private val document = "JESxrqcBkzANTw2NbdBg"
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonRegister.setOnClickListener{
            val username = binding.registerUsername.text.toString()
            val password = binding.registerPassword.text.toString()
            val githubUsername = binding.registerGithubUsername.text.toString()
            val nim = binding.registerNim.text.toString()
            val email = binding.registerEmail.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && githubUsername.isNotEmpty() && nim.isNotEmpty() && email.isNotEmpty()){

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){

                        getBackgroundColor()
                        val randIndex = Random.nextInt(backgroundColor.length())
                        val data = hashMapOf(
                            resources.getString(R.string.field_bg_color) to randIndex,
                            resources.getString(R.string.field_username) to username,
                            resources.getString(R.string.field_githubusername) to githubUsername,
                            resources.getString(R.string.field_nim) to nim,
                            resources.getString(R.string.field_email) to email
                        )

                        db.collection(collection).document(document)
                            .set(data)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Gagal mengakses Firestore!", Toast.LENGTH_SHORT).show()
                            }

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        val errorMessage = (it.exception as FirebaseAuthException).errorCode
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "Please complete the register!", Toast.LENGTH_SHORT).show()
            }
        }

        // show password when click
        val checkbox = findViewById<CheckBox>(R.id.register_show_password)
        val password = findViewById<EditText>(R.id.register_password)
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

    private fun getBackgroundColor(){
        backgroundColor = resources.obtainTypedArray(R.array.integer_profile_user_color)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}