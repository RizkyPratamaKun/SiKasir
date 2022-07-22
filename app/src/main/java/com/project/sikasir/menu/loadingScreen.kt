package com.project.sikasir.menu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.project.sikasir.R
import com.project.sikasir.transaksi.transaksi.transaksi

class loadingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_screen)

        /* Making the loading screen fullscreen. */
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        /* A function that will run the code inside the curly braces after the time specified in
        milliseconds. */
        Handler().postDelayed({
            val intent = Intent(this, transaksi::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}