package com.project.sikasir.transaksi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

/**
 * Dibuat oleh RizkyPratama pada 21-Jun-22.
 */
class adapterKeranjang(private val listKeranjang: ArrayList<classKeranjang>) : RecyclerView.Adapter<adapterKeranjang.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterKeranjang.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_keranjang, parent, false)
        return adapterKeranjang.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: adapterKeranjang.MyViewHolder, position: Int) {

        val currentitem = listKeranjang[position]

        val Nama_Produk = currentitem.Nama_Produk
        val Jumlah_Produk = currentitem.Jumlah_Produk
        val Harga = currentitem.Harga
        val Total = currentitem.Total

        holder.Nama.text = Nama_Produk
        holder.Jumlah_Produk.text = Jumlah_Produk
        holder.Harga.text = Harga
        holder.Total.text = Total

        holder.itemView.setOnClickListener {
            println(Nama_Produk + Jumlah_Produk + Harga + Total)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView = itemView.findViewById(R.id.tv_nama)
        val Harga: TextView = itemView.findViewById(R.id.tv_harga)
        val Jumlah_Produk: TextView = itemView.findViewById(R.id.tv_jumlah)
        val Total: TextView = itemView.findViewById(R.id.tv_total)
    }

    override fun getItemCount(): Int {
        return listKeranjang.size
    }
}