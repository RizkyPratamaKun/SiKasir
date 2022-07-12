package com.project.sikasir.produk.kategori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterKategori(private val listClassKategori: ArrayList<classKategori>) : RecyclerView.Adapter<adapterKategori.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_produk_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = listClassKategori[position]

        holder.Nama.text = currentitem.Nama_Kategori

        holder.itemView.setOnClickListener {
            val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager
            //Bundle (adapter to DialogFragment)
            val bundle = Bundle()
            val dialogFragment = onClickTambahKategori()
            //Tembak
            bundle.putString("Nama_Kategori", currentitem.Nama_Kategori)
            bundle.putString("Edit", "true")
            //GO
            dialogFragment.arguments = bundle
            dialogFragment.show(manager, "dialog_event")
        }
    }

    override fun getItemCount(): Int {
        return listClassKategori.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView = itemView.findViewById(R.id.tv_listkategori)
    }
}