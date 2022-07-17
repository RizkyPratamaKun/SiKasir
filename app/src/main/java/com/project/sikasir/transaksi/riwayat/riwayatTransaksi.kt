package com.project.sikasir.transaksi.riwayat

import android.content.Context
import android.content.Intent
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
import com.project.sikasir.laporan.laporan
import com.project.sikasir.menu.aboutMe
import com.project.sikasir.menu.dashboard
import com.project.sikasir.navPack.ClickListener
import com.project.sikasir.navPack.NavigationItemModel
import com.project.sikasir.navPack.NavigationRVAdapter
import com.project.sikasir.navPack.RecyclerTouchListener
import com.project.sikasir.pegawai.pegawai
import com.project.sikasir.produk.viewpager.viewPagerMenu
import com.project.sikasir.transaksi.pengaturan
import com.project.sikasir.transaksi.transaksi.transaksi
import kotlinx.android.synthetic.main.transaksi_riwayat.*

class riwayatTransaksi : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter

    //FIREBASE
    private lateinit var dataRiwayat: ArrayList<classRiwayat>
    private lateinit var dbref: DatabaseReference

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
        setContentView(R.layout.transaksi_riwayat)

        //START TOOLBAR
        navigation_layout.visibility = View.VISIBLE
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(activity_main_toolbar)
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)
        navigation_header_img.setImageResource(R.drawable.logoaida)
        tv_titleitems.text = "Riwayat Transaksi"
        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        startActivity(Intent(this@riwayatTransaksi, dashboard::class.java))
                        finish()
                    }
                    1 -> {
                        startActivity(Intent(this@riwayatTransaksi, viewPagerMenu::class.java))
                    }
                    2 -> {
                        startActivity(Intent(this@riwayatTransaksi, transaksi::class.java))
                        finish()
                    }
                    3 -> {
                        startActivity(Intent(this@riwayatTransaksi, riwayatTransaksi::class.java))
                        finish()
                    }
                    4 -> {
                        startActivity(Intent(this@riwayatTransaksi, pegawai::class.java))
                    }
                    5 -> {
                        startActivity(Intent(this@riwayatTransaksi, laporan::class.java))
                        finish()
                    }
                    6 -> {
                        startActivity(Intent(this@riwayatTransaksi, pengaturan::class.java))
                        finish()
                    }
                    7 -> {
                        startActivity(Intent(this@riwayatTransaksi, aboutMe::class.java))
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
        updateAdapter(0)

        // BottomSheetBehavior
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, activity_main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
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

        //Recycleview
        dataRiwayat = arrayListOf<classRiwayat>()
        getRiwayatTransaksi()
    }


    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapter
    }

    //Back Button
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

    //Get Riwayat Transaksi
    private fun getRiwayatTransaksi() {
        dbref = FirebaseDatabase.getInstance().getReference("Transaksi")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dataRiwayat.clear()
                    for (userSnapshot in snapshot.children) {
                        val riwayat = userSnapshot.getValue(classRiwayat::class.java)
                        dataRiwayat.add(riwayat!!)
                    }
                    rv_riwayat.adapter = adapterRiwayat(dataRiwayat)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
