package com.project.sikasir.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R
import com.project.sikasir.`class`.Kategori

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterkategori(private val listKategori: ArrayList<Kategori>) :
    RecyclerView.Adapter<adapterkategori.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_kategori,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listKategori[position]

        holder.Nama.text = currentitem.Nama_Kategori
    }

    override fun getItemCount(): Int {

        return listKategori.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Nama: TextView = itemView.findViewById(R.id.tv_listkategori)

    }

}