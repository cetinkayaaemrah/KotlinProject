package com.example.kotlinproject.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.widget.FrameLayout
import com.example.kotlinproject.fragments.FragmentFavorite
import com.example.kotlinproject.fragments.FragmentSearch
import com.example.kotlinproject.fragments.FragmentDatabase
import com.example.kotlinproject.R

class Main2Activity : AppCompatActivity() {
    private var content: FrameLayout? = null
    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        init()
    }

    fun init() {

        toolbar = supportActionBar!!
        content = findViewById(R.id.main_container)
        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val fragment = FragmentFavorite.Companion.newInstance()
        addFragment(fragment)
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_favorite -> {

                val fragment = FragmentFavorite.Companion.newInstance()
                addFragment(fragment)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                val fragment = FragmentSearch()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
//            R.id.navigation_show -> {
//                val fragment = FragmentDatabase()
//                addFragment(fragment)
//                return@OnNavigationItemSelectedListener true
//            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            .replace(R.id.main_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }
}
