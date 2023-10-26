package com.example.uts_project

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.uts_project.ui.home.HomeFragment
import com.example.uts_project.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var buttonNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonNavigationView = findViewById(R.id.nav_view)

        buttonNavigationView.setOnItemSelectedListener{
            when(it.itemId){
                R.id.navigation_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.navigation_profile -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        replaceFragment(HomeFragment())

    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}