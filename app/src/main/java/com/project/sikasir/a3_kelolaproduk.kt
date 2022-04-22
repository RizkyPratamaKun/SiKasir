package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.project.sikasir.fragment.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_a3_kelolaproduk.*

class a3_kelolaproduk : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a3_kelolaproduk)

        //        setSupportActionBar(toolbar)
        viewPagerProduk.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(tabLayoutproduk, viewPagerProduk,
            { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Kelola Produk"
                    }
                    1 -> {
                        tab.text = "Kelola Kategori"
                    }
                }
            }).attach()


        tvA3toA2.setOnClickListener { _ ->
            startActivity(Intent(this, a2_menu::class.java))
            finish()
            //do what you want after click inside here
        }

    }
}

