package com.example.studentcontactapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        
        if (savedInstanceState == null) {
            loadFragment(HomeFragment(), "Student Directory")
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment(), "Student Directory")
                    true
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment(), "Saved Notes")
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment(), "Profile")
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        supportActionBar?.title = title
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
