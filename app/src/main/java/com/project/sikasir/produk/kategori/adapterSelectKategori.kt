package com.project.sikasir.produk.kategori

import android.content.Intent
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SpinnerAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R
import com.project.sikasir.produk.produk.kelolaProduk
import kotlinx.android.synthetic.main.produk_kelola.view.*

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterSelectKategori(private val listClassKategori: ArrayList<classKategori>) :
        RecyclerView.Adapter<adapterSelectKategori.MyViewHolder>(), SpinnerAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_produk_row,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = listClassKategori[position]

        holder.Nama.text = currentitem.Nama_Kategori

        holder.itemView.setOnClickListener {
            val namakategori = listClassKategori[position].Nama_Kategori
            val namamerek = holder.itemView.edMerek.text.toString()

            val intent = Intent(holder.itemView.context, kelolaProduk::class.java)
            intent.putExtra("tambahkategori", namakategori)
            intent.putExtra("tambahmerek", namamerek)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listClassKategori.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView = itemView.findViewById(R.id.tv_listkategori)
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }
}