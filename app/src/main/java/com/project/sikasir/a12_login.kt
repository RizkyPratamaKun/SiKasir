package com.project.sikasir

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_a11_login.*
import kotlinx.android.synthetic.main.sheet_bottomlogin.*
import kotlinx.android.synthetic.main.sheet_bottomtransaksi.bottomSheet

class a12_login : AppCompatActivity() {
    private lateinit var reference: DatabaseReference
    private var USERNAME_KEY = "username_key"
    private var username_key = ""
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a11_login)

        ivhiddenbutton.setOnClickListener {
            btn_reg.visibility = View.VISIBLE
        }

        /* Used to call the bottom sheet. */
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        /* This code is used to change the state of the bottom sheet. */
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            override fun onStateChanged(bottomSheet: View, newState: Int) {}
        })

        //menuju balaman Register(Hidden)
        btn_reg.setOnClickListener(View.OnClickListener {
            intent = Intent(this, a13_register::class.java)
            startActivity(intent)
        })

        //menuju dashboard
        btn_login.setOnClickListener(View.OnClickListener {
            // ubah state menjadi loading
            btn_login.isEnabled = false
            btn_login.text = "Loading ..."

            val dUsername: String = etEmailLog.text.toString()
            val dPassword: String = etPassLog.text.toString()

            //Username kosong
            if (dUsername.isEmpty()) {
                etEmailLog.error = "Username tidak boleh kosong"
                btn_login.isEnabled = true
                btn_login.text = "SIGN IN"
            } else {
                //password kosong
                if (dPassword.isEmpty()) {
                    etPassLog.error = "Pin tidak boleh kosong"
                    btn_login.isEnabled = true
                    btn_login.text = "SIGN IN"
                } else {
                    if (dPassword.length < 6) {
                        etPassLog.error = "Pin harus 6 digit angka"
                        btn_login.isEnabled = true
                        btn_login.text = "SIGN IN"
                    } else {
                        reference = FirebaseDatabase.getInstance()
                            .reference
                            .child("Pegawai")
                            .child(dUsername)
                        reference.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                //Jika data ada dan uname tidak kosong
                                if (dataSnapshot.exists() && dUsername != "") {
                                    //Toast.makeText(this@a12_login, "Username Ditemukan", Toast.LENGTH_SHORT).show()
                                    //Ambil data dari firebase (password)
                                    val passwordFromFirebase: String =
                                        dataSnapshot.child("Pin").value.toString()

                                    //validasi password firebase
                                    if (dPassword == passwordFromFirebase) {
                                        //menyimpan kepada lokal storage/smartphone
                                        val sharedPreferences: SharedPreferences =
                                            getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
                                        val editor: SharedPreferences.Editor =
                                            sharedPreferences.edit()
                                        editor.putString(username_key, dUsername).apply()

                                        //berpindah activity
                                        val gotoHomeIntent =
                                            Intent(this@a12_login, a2_menu::class.java)
                                        startActivity(gotoHomeIntent)
                                        finish()
                                        Toast.makeText(
                                            this@a12_login,
                                            "Selamat datang " + dUsername,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        //Password salah
                                    } else {
                                        btn_login.isEnabled = true
                                        btn_login.text = "SIGN IN"
                                        Toast.makeText(
                                            this@a12_login,
                                            "Username atau Password Salah",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    //Username/Pass salah
                                } else {
                                    btn_login.isEnabled = true
                                    btn_login.text = "SIGN IN"
                                    Toast.makeText(
                                        this@a12_login,
                                        "Username atau Password Salah",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {

                            }
                        })
                    }
                }
            }
        })
    }
}