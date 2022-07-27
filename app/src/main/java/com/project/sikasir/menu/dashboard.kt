package com.project.sikasir.menu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import com.project.sikasir.R
import com.project.sikasir.laporan.laporan
import com.project.sikasir.navPack.ClickListener
import com.project.sikasir.navPack.NavigationItemModel
import com.project.sikasir.navPack.NavigationRVAdapter
import com.project.sikasir.navPack.RecyclerTouchListener
import com.project.sikasir.pegawai.pegawai
import com.project.sikasir.produk.viewpager.viewPagerMenu
import com.project.sikasir.transaksi.pembayaran.classDetailTransaksi
import com.project.sikasir.transaksi.pembayaran.classTransaksi
import com.project.sikasir.transaksi.pengaturan
import com.project.sikasir.transaksi.riwayat.riwayatTransaksi
import com.project.sikasir.transaksi.transaksi.transaksi
import kotlinx.android.synthetic.main.dashhboard.*
import kotlinx.android.synthetic.main.sheet_bottommenu.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class dashboard : AppCompatActivity() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var ssTransaksi: DataSnapshot
    private lateinit var ssDetail: DataSnapshot
    private lateinit var ssProduk: DataSnapshot
    private var awal = -1L
    private var akhir = -1L
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""
    private lateinit var reference: DatabaseReference
    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: NavigationRVAdapter
    val Rp = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashhboard)
        getUsernameLocal()
        getData()
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())

        tv_lihatsemua.visibility = View.GONE
        textView9.text = currentDate

        onClick()
        bottomSheetDashboard()
        navigationLayout()
    }

    private fun bottomSheetDashboard() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetTransaksi)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //menghilangkan textview lihat semua
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> tv_lihatsemua.visibility = View.VISIBLE
                    BottomSheetBehavior.STATE_COLLAPSED -> tv_lihatsemua.visibility = View.GONE
                }
            }
        })
    }

    private fun navigationLayout() {
        navigation_layout.visibility = View.VISIBLE
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(activity_main_toolbar)
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)
        navigation_header_img.setImageResource(R.drawable.logoaida)
        tv_titleitems.text = "Beranda"

        updateAdapter(0)

        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, activity_main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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

    private fun getUsernameLocal() {
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }

    private fun updateAdapter(highlightItemPos: Int) {
        reference = FirebaseDatabase.getInstance().reference.child("Pegawai").child(username_key_new)
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_namaakun.text = dataSnapshot.child("Nama_Pegawai").value.toString()
                tv_nmjabatan.text = dataSnapshot.child("Nama_Jabatan").value.toString()

                val hak = dataSnapshot.child("Hak_Akses").value.toString()
                if (hak == "Pegawai") {
                    cvPegawai.visibility = View.GONE
                    cvLaporan.visibility = View.GONE
                    val items = arrayListOf(
                        NavigationItemModel(R.drawable.ic_baseline_home_24, "Beranda"),
                        NavigationItemModel(R.drawable.ic_baseline_camera_alt_24, "Kelola Produk"),
                        NavigationItemModel(R.drawable.ic_baseline_receipt_24, "Transaksi"),
                        NavigationItemModel(R.drawable.ic_baseline_receipt_long_24, "Riwayat Transaksi"),
                        NavigationItemModel(R.drawable.ic_baseline_settings_24, "Pengaturan")
                    )
                    adapter = NavigationRVAdapter(items, highlightItemPos)
                    navigation_rv.adapter = adapter

                    navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this@dashboard, object : ClickListener {
                        override fun onClick(view: View, position: Int) {
                            when (position) {
                                0 -> {
                                    startActivity(Intent(this@dashboard, dashboard::class.java))
                                    finish()
                                }
                                1 -> {
                                    startActivity(Intent(this@dashboard, viewPagerMenu::class.java))
                                }
                                2 -> {
                                    startActivity(Intent(this@dashboard, transaksi::class.java))
                                    finish()
                                }
                                3 -> {
                                    startActivity(Intent(this@dashboard, riwayatTransaksi::class.java))
                                    finish()
                                }
                                4 -> {
                                    startActivity(Intent(this@dashboard, pengaturan::class.java))
                                    finish()
                                }
                            }
                            if (position != 6 && position != 4) {
                                updateAdapter(position)
                            }
                            Handler().postDelayed({}, 200)
                        }
                    }))
                } else {
                    val items = arrayListOf(
                        NavigationItemModel(R.drawable.ic_baseline_home_24, "Beranda"),
                        NavigationItemModel(R.drawable.ic_baseline_camera_alt_24, "Kelola Produk"),
                        NavigationItemModel(R.drawable.ic_baseline_receipt_24, "Transaksi"),
                        NavigationItemModel(R.drawable.ic_baseline_receipt_long_24, "Riwayat Transaksi"),

                        NavigationItemModel(R.drawable.ic_baseline_people_24, "Pegawai"),
                        NavigationItemModel(R.drawable.ic_baseline_corporate_fare_24, "Laporan"),

                        NavigationItemModel(R.drawable.ic_baseline_settings_24, "Pengaturan"),
                        NavigationItemModel(R.drawable.ic_baseline_account_circle_24, "Tentang Saya")
                    )
                    adapter = NavigationRVAdapter(items, highlightItemPos)
                    navigation_rv.adapter = adapter

                    navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this@dashboard, object : ClickListener {
                        override fun onClick(view: View, position: Int) {
                            when (position) {
                                0 -> {
                                    startActivity(Intent(this@dashboard, dashboard::class.java))
                                    finish()
                                }
                                1 -> {
                                    startActivity(Intent(this@dashboard, viewPagerMenu::class.java))
                                }
                                2 -> {
                                    startActivity(Intent(this@dashboard, transaksi::class.java))
                                    finish()
                                }
                                3 -> {
                                    startActivity(Intent(this@dashboard, riwayatTransaksi::class.java))
                                    finish()
                                }
                                4 -> {
                                    startActivity(Intent(this@dashboard, pegawai::class.java))
                                }
                                5 -> {
                                    startActivity(Intent(this@dashboard, laporan::class.java))
                                    finish()
                                }
                                6 -> {
                                    startActivity(Intent(this@dashboard, pengaturan::class.java))
                                    finish()
                                }
                                7 -> {
                                    startActivity(Intent(this@dashboard, aboutMe::class.java))
                                }
                            }
                            if (position != 6 && position != 4) {
                                updateAdapter(position)
                            }
                            Handler().postDelayed({}, 200)
                        }
                    }))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

    private fun onClick() {
        //LihatSemua
        tv_lihatsemua.setOnClickListener { startActivity(Intent(this@dashboard, laporan::class.java)) }
        //CardViewTransaksi
        cvTransaksi.setOnClickListener { startActivity(Intent(this, transaksi::class.java)) }
        //CardViewPengaturan
        cvPengaturan.setOnClickListener { startActivity(Intent(this, pengaturan::class.java)) }
        //CardViewProduk
        cvProduk.setOnClickListener { startActivity(Intent(this, viewPagerMenu::class.java)) }
        //CardViewPegawai
        cvPegawai.setOnClickListener { startActivity(Intent(this, pegawai::class.java)) }
        //CardViewLaporan
        cvLaporan.setOnClickListener { startActivity(Intent(this, laporan::class.java)) }
        //FloatingActionButton Tambah Transaksi
        extendtambahtransaksi.setOnClickListener { startActivity(Intent(this, transaksi::class.java)) }
        //profile
        clProfil.setOnClickListener { startActivity(Intent(this, profile::class.java)) }
    }

    private fun getData() {

        val refTransaksi = FirebaseDatabase.getInstance().getReference("Transaksi").orderByValue()
        val refDetail = FirebaseDatabase.getInstance().getReference("DetailTransaksi")
        val refProduk = FirebaseDatabase.getInstance().getReference("Produk")

        refDetail.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshotDetail: DataSnapshot) {
                if (snapshotDetail.exists()) {
                    refTransaksi.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshotTransaksi: DataSnapshot) {
                            if (snapshotTransaksi.exists()) {
                                refProduk.addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(snapshotProduk: DataSnapshot) {
                                        if (snapshotProduk.exists()) {
                                            ssDetail = snapshotDetail
                                            ssTransaksi = snapshotTransaksi
                                            ssProduk = snapshotProduk
                                            initAdapter()
                                        }
                                    }

                                    override fun onCancelled(error: DatabaseError) {}
                                })
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {}
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun initAdapter() {
        if (this::ssTransaksi.isInitialized && this::ssDetail.isInitialized && this::ssProduk.isInitialized) {
            var jumlahProduk = 0
            var totalTransaksi = 0

            //jika awal dan akhir kosong
            if (awal == -1L && akhir == -1L) {
                for (snapDetail in ssDetail.children) {
                    val cd = snapDetail.getValue(classDetailTransaksi::class.java)
                    jumlahProduk += cd?.jumlah_Produk?.toInt()!!
                    totalTransaksi += cd.total?.replace(",00", "")?.filter { it.isDigit() }?.toInt()!!
                }
            } else {
                for (snapDetail in ssDetail.children) {
                    for (snapTransaksi in ssTransaksi.children) {
                        val t = snapTransaksi.getValue(classTransaksi::class.java)
                        val cd = snapDetail.getValue(classDetailTransaksi::class.java)
                        //jika tanggal lebih dari sama dengan awal dan tanggal kurang dari sama dengan akhir
                        if (t?.tanggal!! >= awal && t.tanggal!! <= akhir) {
                            jumlahProduk += cd?.jumlah_Produk?.toInt()!!
                            totalTransaksi += cd.total?.replace(",00", "")?.filter { it.isDigit() }?.toInt()!!
                        }
                    }
                }
            }

            val totalString = Rp.format(totalTransaksi)
            val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

            tv_jumlah_produk_today.text = "$jumlahProduk Produk"
        }
    }
}