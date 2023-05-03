package com.example.inspirationalshibe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class InspirationalShibeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspirational_shibe)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, InspirationalShibeFragment.newInstance())
                .commit()
        }
    }
}