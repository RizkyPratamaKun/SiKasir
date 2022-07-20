package com.project.sikasir.transaksi.pembayaran

import com.project.sikasir.transaksi.keranjang.classKeranjang

data class classTransaksi(
    var tanggal: String? = null,
    var namaPegawai: String? = null,
    var produk: ArrayList<classKeranjang> = ArrayList()
)