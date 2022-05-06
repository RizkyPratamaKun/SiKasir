package com.project.sikasir

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_a6_transaksi.*
import kotlinx.android.synthetic.main.sheet_bottomtransaksi.*

class a6_transaksi : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a6_transaksi)

        /* This code is used to call the ScanBarcodeTransaksi activity. */
        ivToQR.setOnClickListener {
            val intent = Intent(this, ScanBarcodeTransaksi::class.java)
            startActivity(intent)
        }

        /* Used to call the bottom sheet. */
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        /* This code is used to change the state of the bottom sheet. */
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            /**
             * `onSlide` is called when the user slides the bottom sheet up or down
             *
             * @param bottomSheet The bottom sheet view.
             * @param slideOffset A value from 0 to 1, where 0 is completely collapsed and 1 is
             * completely expanded.
             */

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            /**
             * The above function is a function that is used to change the state of the bottom sheet.
             *
             * @param bottomSheet View, newState: Int
             * @param newState The new state of the bottom sheet.
             */
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> btnSimpanTransaksi.visibility =
                        View.VISIBLE
                    BottomSheetBehavior.STATE_COLLAPSED -> btnSimpanTransaksi.visibility = View.GONE
                }
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> btnTagih.text = "Bayar"
                    BottomSheetBehavior.STATE_COLLAPSED -> btnTagih.text = "Tagih"
                }
            }
        })

        /* This code is used to change the state of the bottom sheet. */
        btnTagih.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

    }

}
