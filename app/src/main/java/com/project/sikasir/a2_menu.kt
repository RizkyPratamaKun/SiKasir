package com.project.sikasir

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
import com.project.sikasir.navPack.ClickListener
import com.project.sikasir.navPack.NavigationItemModel
import com.project.sikasir.navPack.NavigationRVAdapter
import com.project.sikasir.navPack.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_a2_menu.*

class a2_menu : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter

    private var items = arrayListOf(
        NavigationItemModel(R.drawable.ic_baseline_home_24, "Beranda"),
        NavigationItemModel(R.drawable.ic_baseline_camera_alt_24, "Kelola Produk"),
        NavigationItemModel(R.drawable.ic_baseline_receipt_24, "Transaksi"),
        NavigationItemModel(R.drawable.ic_baseline_receipt_long_24, "Riwayat Transaksi"),
        NavigationItemModel(R.drawable.ic_baseline_people_24, "Pegawai"),
        NavigationItemModel(R.drawable.ic_baseline_corporate_fare_24, "Laporan"),
        NavigationItemModel(R.drawable.ic_baseline_settings_24, "Settings")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a2_menu)

        cvProduk.setOnClickListener { _ ->
            startActivity(Intent(this, a3_kelolaproduk::class.java))
            finish()
        }
        cvPegawai.setOnClickListener { _ ->
            startActivity(Intent(this, a5_kelolapegawai::class.java))
            finish()
        }
        cvPanduanPengguna.setOnClickListener { _ ->
            startActivity(Intent(this, a3_kelolaproduk::class.java))
            finish()
        }
        flTambahTransaksi.setOnClickListener { _ ->
            startActivity(Intent(this, a6_transaksi::class.java))
            finish()
        }

        //START TOOLBAR
        /* Assigning the `drawer_layout` to the `drawerLayout` variable. */
        drawerLayout = findViewById(R.id.drawer_layout)
        // Set the toolbar
        setSupportActionBar(activity_main_toolbar)
        // Setup Recyclerview's Layout
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)

        // Add Item Touch Listener
        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        val intent = Intent(this@a2_menu, a2_menu::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this@a2_menu, a3_kelolaproduk::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this@a2_menu, a6_transaksi::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        finish()
                    }
                    4 -> {
                        val intent = Intent(this@a2_menu, a5_kelolapegawai::class.java)
                        startActivity(intent)
                    }
                    5 -> {
                        val intent = Intent(this@a2_menu, a10_laporan::class.java)
                        startActivity(intent)
                    }
                    6 -> {
                        finish()
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
            this,
            drawerLayout,
            activity_main_toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        // Set Header Image
        navigation_header_img.setImageResource(R.drawable.logoaida)
        tv_titleitems.text = "B"
    }

    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapter
        adapter.notifyDataSetChanged()
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
