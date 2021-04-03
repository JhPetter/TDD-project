package com.petter.testapplication.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.petter.testapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, PlaylistFragment.newInstance())
                    .commit()
        }
    }
}