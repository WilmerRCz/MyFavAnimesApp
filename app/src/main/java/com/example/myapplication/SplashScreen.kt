package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val delay: Long = 3000
        Handler(this.mainLooper).postDelayed(
            {
            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            delay
        )

    }
}