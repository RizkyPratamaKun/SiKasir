package com.project.sikasir.transaksi.pembayaran

import com.project.sikasir.transaksi.keranjang.classKeranjang

data class classPembayaran(
    var tanggal: MutableMap<String, String>,
    var emailPegawai: String? = null,
    var jabatan: String? = null,
    var total: String? = null,
    var diskon: String? = null,
    var total_Modal: String? = null,
    var produk: ArrayList<classKeranjang> = ArrayList()
)