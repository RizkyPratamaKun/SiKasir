package com.project.sikasir.produk.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.sikasir.produk.kategori.menuKategori
import com.project.sikasir.produk.produk.produk

/**
 * Dibuat oleh RizkyPratama pada 12-Apr-22.
 */

class viewpageradapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        //2 jumlah tabs
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return produk()
            1 -> return menuKategori()
        }
        return produk()
    }
}