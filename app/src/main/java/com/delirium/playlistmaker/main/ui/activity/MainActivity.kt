package com.delirium.playlistmaker.main.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.main.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragment_container_view, MainFragment.newInstance())
            }
        }

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
//        val navController = navHostFragment.navController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
    }
}