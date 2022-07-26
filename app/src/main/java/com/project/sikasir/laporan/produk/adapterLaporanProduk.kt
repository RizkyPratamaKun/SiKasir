package com.project.sikasir.laporan.produk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterLaporanProduk(private val listProduk: ArrayList<classLapProduk>) :
        RecyclerView.Adapter<adapterLaporanProduk.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_riwayat_produk, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listProduk[position]

        val Nama_Produk = currentitem.namaProduk
        val Harga_Jual = currentitem.harga
        val Stok = currentitem.sisaProduk
        val Terjual = currentitem.terjual

        holder.Nama.text = Nama_Produk
        holder.Harga.text = Harga_Jual
        holder.terjual.text = Terjual.toString()
        holder.stok.text = Stok.toString()
    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Nama: TextView = itemView.findViewById(R.id.riwayat_tv_nama)
        val Harga: TextView = itemView.findViewById(R.id.riwayat_tv_hargai)
        val terjual: TextView = itemView.findViewById(R.id.riwayat_tv_terjual)
        val stok: TextView = itemView.findViewById(R.id.riwayat_tv_sisa)

    }

}