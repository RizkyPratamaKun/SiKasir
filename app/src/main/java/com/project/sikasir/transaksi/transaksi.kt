package com.project.sikasir.transaksi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
import com.project.sikasir.produk.kategori.classKategori
import com.project.sikasir.produk.produk.classProduk
import com.project.sikasir.produk.viewpager.viewPagerMenu
import kotlinx.android.synthetic.main.sheet_bottomtransaksi.*
import kotlinx.android.synthetic.main.transaksi_menu.*
import java.text.NumberFormat
import java.util.*

class transaksi : AppCompatActivity() {
    private lateinit var refPegawai: DatabaseReference

    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private var username_key_new = ""

    private lateinit var dataKategori: ArrayList<classKategori>
    val keranjangList = ArrayList<classKeranjang>()
    val produkList = ArrayList<classProduk>()

    lateinit var dataSpinStringKategori: ArrayList<String>
    private val produkAdapter: adapterSearchTransaksi by lazy {
        adapterSearchTransaksi(produkList)
    }

    private lateinit var adapterRV: NavigationRVAdapter
    lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val trasaksiListener = object : adapterTransaksi.TransaksiListener {
        override fun AddToCart(produk: classProduk) {

            val refKeranjang = FirebaseDatabase.getInstance().getReference("Keranjang")

            produk.Nama_Produk?.let {
                refKeranjang.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val keranjang = snapshot.getValue(classKeranjang::class.java)

                            if (keranjang != null) {
                                //Set Jumlah di Keranjang
                                keranjang.Jumlah_Produk = (keranjang.Jumlah_Produk?.toInt()?.plus(1)).toString()

                                //Kalkulasi
                                val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
                                    .format((keranjang.Harga!!.replace(".", "").replace("Rp ", "").toDouble() * keranjang.Jumlah_Produk!!.toDouble()))

                                //SetCurrency
                                keranjang.Total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

                                refKeranjang.child(produk.Nama_Produk!!).setValue(keranjang)
                            }
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        } else {
                            refKeranjang.child(produk.Nama_Produk!!).setValue(classKeranjang(produk.Nama_Produk, produk.Harga_Jual, "1", produk.Harga_Jual))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaksi_menu)

        getUsernameLocal()
        getNamaPegawai()
        getKeranjang()
        bottomSheetBehavior()
        navigation_rv()
        getQR()
        onClick()

        if (edCariProdukTransaksi.text.isEmpty()) {
            getProduk()
        }
    }

    private fun onClick() {
        ivToQR.setOnClickListener {
            startActivity(Intent(this, scanBarcodeTambahTransaksi::class.java))
        }

        edCariProdukTransaksi.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                getProduk()
                if (s.toString().isEmpty()) {
                    getProduk()
                } else {
                    produkList.clear()
                    searchByName(edCariProdukTransaksi.text.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })
    }

    private fun getQR() {
        val QR: String = intent.getStringExtra("DataQR").toString()
        if (QR == "null") {
            edCariProdukTransaksi.setText("")
        } else {
            edCariProdukTransaksi.setText(QR)
        }
    }

    fun getUsernameLocal() {
        val sharedPreference: SharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
        username_key_new = sharedPreference.getString(username_key, "").toString()
    }

    private fun updateAdapter(highlightItemPos: Int) {
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
        adapterRV = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapterRV
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

    private fun searchByName(search: String) {
        val refSearch = FirebaseDatabase.getInstance().getReference("Produk")
        refSearch.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val prdk = i.getValue(classProduk::class.java)
                        if (prdk!!.Nama_Produk == search) {
                            produkList.add(prdk)
                        }
                    }
                    produkAdapter.submitList(produkList)
                    rv_transaksi.adapter = adapterTransaksi(produkList, trasaksiListener)
                } else {
                    Toast.makeText(applicationContext, "Data does not exist", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getKeranjang() {
        rv_keranjang.layoutManager = GridLayoutManager(this, 1)
        rv_keranjang.setHasFixedSize(true)

        val refKeranjang = FirebaseDatabase.getInstance().getReference("Keranjang")

        refKeranjang.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    rv_keranjang.visibility = View.VISIBLE
                    cl_keranjang_kosong.visibility = View.GONE
                    keranjangList.clear()
                    var i = 0
                    var sum = 0
                    for (snapKeranjang in snapshot.children) {
                        //DataKeranjang
                        val keranjang = snapKeranjang.getValue(classKeranjang::class.java)
                        keranjangList.add(keranjang!!)
                        //Total Harga di Keranjang
                        sum += Integer.parseInt(snapKeranjang.child("total").getValue(String::class.java)!!.replace(",00", "").replace(".", "").replace("Rp ", ""))
                        //total barang di keranjang
                        i += 1
                    }
                    val totalString = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(sum)
                    val total = totalString.substring(0, 2) + " " + totalString.substring(2, totalString.length)

                    tv_subtotal_keranjang.text = total
                    btnTagih.text = total

                    tv_jmlBarang.text = i.toString()
                    rv_keranjang.adapter = adapterKeranjang(keranjangList)

                    btnTagih.setOnClickListener {
                        val intent = Intent(this@transaksi, pembayaranTunai::class.java)
                        intent.putExtra("tagihan", btnTagih.text.toString())
                        startActivity(intent)
                        finish()
                    }
                } else {
                    cl_keranjang_kosong.visibility = View.VISIBLE
                    rv_keranjang.visibility = View.GONE
                    btnTagih.setOnClickListener {
                        Toast.makeText(this@transaksi, "Isi keranjang terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getProduk() {
        rv_transaksi.layoutManager = GridLayoutManager(this, 2)
        rv_transaksi.setHasFixedSize(true)
        val refProduk = FirebaseDatabase.getInstance().getReference("Produk")

        refProduk.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    rv_transaksi.visibility = View.VISIBLE
                    cl_produk_kosong.visibility = View.GONE
                    produkList.clear()
                    for (snapshot in snapshot.children) {
                        val kat = snapshot.getValue(classProduk::class.java)
                        produkList.add(kat!!)
                    }
                    rv_transaksi.adapter = adapterTransaksi(produkList, trasaksiListener)
                } else {
                    cl_produk_kosong.visibility = View.VISIBLE
                    rv_transaksi.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun getKategori() {
        dataKategori = arrayListOf<classKategori>()
        val dbref = FirebaseDatabase.getInstance().getReference("Kategori")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snapshot in snapshot.children) {
                        val dataset = snapshot.child("Nama_Kategori").getValue(String::class.java)
                        if (dataset != null) {
                            dataSpinStringKategori.add(dataset)
                        }
                    }
                    val arrayAdapter = ArrayAdapter(this@transaksi, android.R.layout.simple_spinner_item, dataSpinStringKategori)
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinKategori.adapter = arrayAdapter
                } else {
                    Toast.makeText(applicationContext, "Kategori Tidak Ada", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun bottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetTransaksi)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (tv_jmlBarang.text == "0") {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> btnTagih.visibility = View.VISIBLE
                        BottomSheetBehavior.STATE_COLLAPSED -> btnTagih.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> ivkosong.visibility = View.VISIBLE
                        BottomSheetBehavior.STATE_COLLAPSED -> ivkosong.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> tvkosong.visibility = View.VISIBLE
                        BottomSheetBehavior.STATE_COLLAPSED -> tvkosong.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> rv_keranjang.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> cl_total.visibility = View.GONE
                    }
                } else {
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> btnTagih.visibility = View.VISIBLE
                        BottomSheetBehavior.STATE_COLLAPSED -> btnTagih.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> ivkosong.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> tvkosong.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> rv_keranjang.visibility = View.VISIBLE
                        BottomSheetBehavior.STATE_COLLAPSED -> rv_keranjang.visibility = View.GONE
                    }
                    when (newState) {
                        BottomSheetBehavior.STATE_EXPANDED -> cl_total.visibility = View.VISIBLE
                        BottomSheetBehavior.STATE_COLLAPSED -> cl_total.visibility = View.GONE
                    }
                }
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> btnTagih.text = tv_jmlBarang.text.toString() + " Barang"
                }
            }
        })
    }

    fun getNamaPegawai() {
        refPegawai = FirebaseDatabase.getInstance().reference.child("Pegawai").child(username_key_new)
        refPegawai.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tv_namaakun.text = dataSnapshot.child("Nama_Pegawai").value.toString()
                tv_nmjabatan.text = dataSnapshot.child("Nama_Jabatan").value.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun navigation_rv() {
        navigation_layout.visibility = View.VISIBLE
        drawerLayout = findViewById(R.id.drawer_layout)
        setSupportActionBar(activity_main_toolbar)
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)
        navigation_header_img.setImageResource(R.drawable.logoaida)
        tv_titleitems.text = "Transaksi"

        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        startActivity(Intent(this@transaksi, dashboard::class.java))
                        finish()
                    }
                    1 -> {
                        startActivity(Intent(this@transaksi, viewPagerMenu::class.java))
                    }
                    2 -> {
                        startActivity(Intent(this@transaksi, transaksi::class.java))
                        finish()
                    }
                    3 -> {
                        startActivity(Intent(this@transaksi, riwayatTransaksi::class.java))
                        finish()
                    }
                    4 -> {
                        startActivity(Intent(this@transaksi, pegawai::class.java))
                    }
                    5 -> {
                        startActivity(Intent(this@transaksi, laporan::class.java))
                        finish()
                    }
                    6 -> {
                        startActivity(Intent(this@transaksi, pengaturan::class.java))
                        finish()
                    }
                    7 -> {
                        startActivity(Intent(this@transaksi, aboutMe::class.java))
                    }
                }
                if (position != 6 && position != 4) {
                    updateAdapter(position)
                }
                Handler().postDelayed({
                }, 200)
            }
        }))
        updateAdapter(0)

        val toggle: ActionBarDrawerToggle =
            object : ActionBarDrawerToggle(this, drawerLayout, activity_main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
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
}