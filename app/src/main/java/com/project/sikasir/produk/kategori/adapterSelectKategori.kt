package com.project.sikasir.produk.kategori

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterSelectKategori(private val listClassKategori: ArrayList<classKategori>, private val kategoriListener: TransaksiListener) :
        RecyclerView.Adapter<adapterSelectKategori.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_produk_row, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = listClassKategori[position]

        holder.Nama.text = currentitem.Nama_Kategori

        holder.itemView.setOnClickListener {
            kategoriListener.addtoKategori(currentitem)
        }
    }


    override fun getItemCount(): Int {
        return listClassKategori.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView = itemView.findViewById(R.id.tv_listkategori)
    }

    interface TransaksiListener {
        fun addtoKategori(kategori: classKategori)
    }
}