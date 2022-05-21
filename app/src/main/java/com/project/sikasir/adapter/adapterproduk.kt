package com.project.sikasir.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R
import com.project.sikasir.listview.Produk

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterproduk(private val listPegawai: ArrayList<Produk>) :
    RecyclerView.Adapter<adapterproduk.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_produk,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listPegawai[position]

        holder.Nama.text = currentitem.Nama_Produk
        holder.Harga.text = currentitem.Harga
    }

    override fun getItemCount(): Int {

        return listPegawai.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Nama: TextView = itemView.findViewById(R.id.tv_listnama)
        val Harga: TextView = itemView.findViewById(R.id.tv_listharga)

    }

}