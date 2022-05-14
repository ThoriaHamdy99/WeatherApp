package com.example.android.weatherapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.android.weatherapp.R
import com.example.android.weatherapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }, 4000)
    }
}