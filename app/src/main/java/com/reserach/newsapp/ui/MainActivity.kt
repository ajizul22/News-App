package com.reserach.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.reserach.newsapp.R
import com.reserach.newsapp.databinding.ActivityMainBinding
import com.reserach.newsapp.ui.detail.DetailNewsFragment
import com.reserach.newsapp.ui.home.HomeFragment
import com.reserach.newsapp.util.Constant

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val intent = intent
        val keylayout = intent.getIntExtra("KEY_OPEN_LAYOUT", 0)

        val title = intent.getStringExtra("TITLE")
        val image = intent.getStringExtra("IMAGE")
        val date = intent.getStringExtra("DATE")
        val content = intent.getStringExtra("CONTENT")
        val author = intent.getStringExtra("AUTHOR")

        when (keylayout) {
            Constant.KEY_OPEN_LAYOUT_DETAIL_NEWS -> {
                val fragment = DetailNewsFragment.newInstance(title, author, image, date, content)
                showFragment(fragment)
            }
            else -> {
                val fragment = HomeFragment()
                showFragment(fragment)
            }
        }

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fg_container, fragment).commit()
    }
}