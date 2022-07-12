package com.project.sikasir.laporan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.project.sikasir.R
import com.project.sikasir.menu.aboutMe
import com.project.sikasir.menu.dashboard
import com.project.sikasir.navPack.ClickListener
import com.project.sikasir.navPack.NavigationItemModel
import com.project.sikasir.navPack.NavigationRVAdapter
import com.project.sikasir.navPack.RecyclerTouchListener
import com.project.sikasir.pegawai.pegawai
import com.project.sikasir.produk.viewpager.viewPagerMenu
import com.project.sikasir.transaksi.pengaturan
import com.project.sikasir.transaksi.riwayatTransaksi
import com.project.sikasir.transaksi.transaksi
import kotlinx.android.synthetic.main.laporan_menu.*

class laporan : AppCompatActivity() {
    //RV
    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter

    //Firebase
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private lateinit var reference: DatabaseReference

    private var items = arrayListOf(
        NavigationItemModel(R.drawable.ic_baseline_home_24, "Beranda"),
        NavigationItemModel(R.drawable.ic_baseline_camera_alt_24, "Kelola Produk"),
        NavigationItemModel(R.drawable.ic_baseline_receipt_24, "Transaksi"),
        NavigationItemModel(R.drawable.ic_baseline_receipt_long_24, "Riwayat Transaksi"),
        NavigationItemModel(R.drawable.ic_baseline_people_24, "Pegawai"),
        NavigationItemModel(R.drawable.ic_baseline_corporate_fare_24, "Laporan"),
        NavigationItemModel(R.drawable.ic_baseline_settings_24, "Pengaturan"),
        NavigationItemModel(R.drawable.ic_baseline_account_circle_24, "Tentang Saya")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.laporan_menu)

        //Set
        getUsernameLocal()
        navigation_layout.visibility = View.VISIBLE
        getIdPegawai()

        //OnClick
        cvRangkuman.setOnClickListener {
            val showContent = Intent(applicationContext, laporanRangkuman::class.java)
            startActivity(showContent)
        }
        cvPenjualanPerKategori.setOnClickListener {
            startActivity(Intent(applicationContext, laporanKategori::class.java))
        }
        LaporanPegawai.setOnClickListener {
            startActivity(Intent(applicationContext, laporanPegawai::class.java))
        }

        //START TOOLBAR
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(activity_main_toolbar)
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)
        navigation_header_img.setImageResource(R.drawable.logoaida)
        tv_titleitems.text = "Laporan"

        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        startActivity(Intent(this@laporan, dashboard::class.java))
                        finish()
                    }
                    1 -> {
                        startActivity(Intent(this@laporan, viewPagerMenu::class.java))
                    }
                    2 -> {
                        startActivity(Intent(this@laporan, transaksi::class.java))
                        finish()
                    }
                    3 -> {
                        startActivity(Intent(this@laporan, riwayatTransaksi::class.java))
                        finish()
                    }
                    4 -> {
                        //Tanpa Drawer
                        startActivity(Intent(this@laporan, pegawai::class.java))
                    }
                    5 -> {
                        startActivity(Intent(this@laporan, laporan::class.java))
                        finish()
                    }
                    6 -> {
                        startActivity(Intent(this@laporan, pengaturan::class.java))
                        finish()
                    }
                    7 -> {
                        startActivity(Intent(this@laporan, aboutMe::class.java))
                    }
                }
                // Don't highlight the 'Profile' and 'Like us on Facebook' item row
                if (position != 6 && position != 4) {
                    updateAdapter(position)
                }
                Handler().postDelayed({
                }, 200)
            }
        }))

        // Update Adapter with item data and highlight the default menu item ('Home' Fragment)
        updateAdapter(0)

        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, activity_main_toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun getIdPegawai() {
        reference = FirebaseDatabase.getInstance().reference.child("Pegawai").child(username_key_new)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_namaakun.text = dataSnapshot.child("Nama_Pegawai").value.toString()
                tv_nmjabatan.text = dataSnapshot.child("Nama_Jabatan").value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapter
    }

    fun getUsernameLocal() {
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Go to the previous fragment
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }
}
