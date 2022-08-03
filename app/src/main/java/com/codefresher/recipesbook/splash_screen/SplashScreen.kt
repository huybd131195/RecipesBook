package com.codefresher.recipesbook.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.codefresher.recipesbook.MainActivity
import com.codefresher.recipesbook.R
import com.codefresher.recipesbook.view.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)

val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()

        },3000)
    }
}