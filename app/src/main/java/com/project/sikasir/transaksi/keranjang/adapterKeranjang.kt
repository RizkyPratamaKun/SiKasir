package com.project.sikasir.transaksi.keranjang

import android.content.Intent
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_keranjang, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

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
            val namaProduk = listKeranjang[position].Nama_Produk
            val jumlahProduk = listKeranjang[position].Jumlah_Produk
            val harga = listKeranjang[position].Harga
            val total = listKeranjang[position].Total

            val intent = Intent(holder.itemView.context, kelolaKeranjang::class.java)
            intent.putExtra("Nama_Produk", namaProduk)
            intent.putExtra("jumlahProduk", jumlahProduk)
            intent.putExtra("harga", harga)
            intent.putExtra("total", total)

            holder.itemView.context.startActivity(intent)
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