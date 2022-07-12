package com.project.sikasir.produk.merek

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterMerek(
    private val listMerek: ArrayList<classMerek>
) :
        RecyclerView.Adapter<adapterMerek.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_produk_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listMerek[position]

        //mengambil data dari listclassmerek ke holder
        holder.Nama.text = currentitem.Nama_Merek

        //holder ketika di klik
        holder.itemView.setOnClickListener {
            val Nama_Merek = listMerek[position].Nama_Merek
        }
    }

    override fun getItemCount(): Int {
        return listMerek.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //memilih textview untuk dipasangkan
        val Nama: TextView = itemView.findViewById(R.id.tv_listkategori)
    }

}