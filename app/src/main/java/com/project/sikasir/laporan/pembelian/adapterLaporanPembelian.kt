package com.project.sikasir.laporan.pembelian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R
import java.text.NumberFormat
import java.util.*

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterLaporanPembelian(private val listPembelian: ArrayList<classLapPembelian>) :
        RecyclerView.Adapter<adapterLaporanPembelian.MyViewHolder>() {
    val Rp = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_laporan_pembelian, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listPembelian[position]

        val Nama_Produk = currentitem.namaProduk
        val Harga_Modal = currentitem.harga_Modal
        val jumlah_Produk = currentitem.jumlah_Produk
        val total = currentitem.totalPembelian

        holder.Nama.text = Nama_Produk
        holder.Harga_Modal.text = Harga_Modal
        holder.jumlah_Produk.text = jumlah_Produk.toString()
        holder.total.text = Rp.format(total)
    }

    override fun getItemCount(): Int {
        return listPembelian.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Nama: TextView = itemView.findViewById(R.id.kp_tv_namalist)
        val Harga_Modal: TextView = itemView.findViewById(R.id.tv_hargamodal_pembelian)
        val jumlah_Produk: TextView = itemView.findViewById(R.id.tv_jumlah_beli)
        val total: TextView = itemView.findViewById(R.id.tv_total_pembelian)

    }

}