package com.project.sikasir.transaksi.transaksi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R
import com.project.sikasir.produk.produk.classProduk

/**
 * Dibuat oleh RizkyPratama pada 21-Jun-22.
 */

class adapterTransaksi(private val listTransaksi: ArrayList<classProduk>, private val transaksiListener: TransaksiListener) : RecyclerView.Adapter<adapterTransaksi.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_produk, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = listTransaksi[position]

        val Nama_Produk = currentitem.Nama_Produk
        val Harga_Jual = currentitem.Harga_Jual

        holder.Nama.text = Nama_Produk
        holder.Harga_Jual.text = Harga_Jual

        holder.itemView.setOnClickListener {
            transaksiListener.AddToCart(currentitem)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView = itemView.findViewById(R.id.tv_listnama)
        val Harga_Jual: TextView = itemView.findViewById(R.id.tv_listharga)
    }

    override fun getItemCount(): Int {
        return listTransaksi.size
    }

    interface TransaksiListener {
        fun AddToCart(produk: classProduk)
    }
}