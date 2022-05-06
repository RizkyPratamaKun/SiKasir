package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class a1_loading_screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a1_load)

        /* Making the loading screen fullscreen. */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        /* A function that will run the code inside the curly braces after the time specified in
        milliseconds. */
        Handler().postDelayed({
            val intent = Intent(this, a2_menu::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}