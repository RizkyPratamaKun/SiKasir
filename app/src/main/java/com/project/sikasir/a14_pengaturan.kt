package com.project.sikasir

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.project.sikasir.navPack.ClickListener
import com.project.sikasir.navPack.NavigationItemModel
import com.project.sikasir.navPack.NavigationRVAdapter
import com.project.sikasir.navPack.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_a14_pengaturan.*

class a14_pengaturan : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter

    //FIREBASE
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
        setContentView(R.layout.activity_a14_pengaturan)
        //FIREBASE
        getUsernameLocal()

        reference = FirebaseDatabase.getInstance()
            .reference
            .child("Pegawai")
            .child(username_key_new)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_namaakun.text = dataSnapshot.child("Nama_Pegawai").value.toString()
                tv_nmjabatan.text = dataSnapshot.child("Nama_Jabatan").value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        tvLogoutPengaturan.setOnClickListener { view ->
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Apakah anda yakin untuk keluar?")
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(this@a14_pengaturan, a12_login::class.java)
                    startActivity(intent)
                    finish()
                })
                // negative button text and action
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Keluar")
            // show alert dialog
            alert.show()

        }


        //START TOOLBAR
        /* Assigning the `drawer_layout` to the `drawerLayout` variable. */
        drawerLayout = findViewById(R.id.drawer_layout)
        // Set the toolbar
        setSupportActionBar(activity_main_toolbar)
        // Setup Recyclerview's Layout
        navigation_rv.layoutManager = LinearLayoutManager(this)
        /* A performance optimization. */
        navigation_rv.setHasFixedSize(true)
        // Set Header Image
        navigation_header_img.setImageResource(R.drawable.logoaida)
        /* Setting the text of the TextView with the id `tv_titleitems` to the string `Beranda`. */
        tv_titleitems.text = "Pengaturan"


        /* This is a function that will be executed when the button is clicked. */
        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        val intent = Intent(this@a14_pengaturan, a2_menu::class.java)
                        intent.putExtra("activityName", "Beranda")
                        startActivity(intent)
                        finish()
                    }
                    1 -> {
                        val intent = Intent(this@a14_pengaturan, a3_kelolaproduk::class.java)
                        intent.putExtra("activityName", "Kelola Produk")
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this@a14_pengaturan, a6_transaksi::class.java)
                        intent.putExtra("activityName", "Transaksi")
                        startActivity(intent)
                        finish()
                    }
                    3 -> {
                        val intent = Intent(this@a14_pengaturan, a11_riwayattransaksi::class.java)
                        intent.putExtra("activityName", "Riwayat Transaksi")
                        startActivity(intent)
                        finish()
                    }
                    4 -> {
                        val intent = Intent(this@a14_pengaturan, a5_kelolapegawai::class.java)
                        intent.putExtra("activityName", "Kelola Pegawai")
                        startActivity(intent)
                    }
                    5 -> {
                        val intent = Intent(this@a14_pengaturan, a10_laporan::class.java)
                        intent.putExtra("activityName", "Laporan")
                        startActivity(intent)
                        finish()
                    }
                    6 -> {
                        val intent = Intent(this@a14_pengaturan, a14_pengaturan::class.java)
                        intent.putExtra("activityName", "Pengaturan")
                        startActivity(intent)
                        finish()
                    }
                    7 -> {
                        val intent = Intent(this@a14_pengaturan, a15_about::class.java)
                        intent.putExtra("activityName", "Tentang Saya")
                        startActivity(intent)
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
            /**
             * It hides the keyboard when the drawer is closed.
             *
             * @param drawerView The drawer view that is now closed.
             */
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

            /**
             * It hides the keyboard when the drawer is opened.
             *
             * @param drawerView The drawer view that is now open
             */
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
        /* Adding a listener to the `drawerLayout` variable. */
        drawerLayout.addDrawerListener(toggle)
        /* Used to synchronize the state of the drawer indicator/affordance with the linked
        DrawerLayout. */
        toggle.syncState()
    }

    /**
     * > This function updates the adapter with the new list of items and the position of the item to
     * be highlighted
     *
     * @param highlightItemPos The position of the item in the list that you want to highlight.
     */
    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun getUsernameLocal() {
        val sharedPreference: SharedPreferences =
            getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
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
