package com.project.sikasir.laporan.riwayat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

class adapterRiwayat(private val listClassRiwayat: ArrayList<classRiwayat>) :
        RecyclerView.Adapter<adapterRiwayat.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_riwayat, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listClassRiwayat[position]

        holder.nama.text = currentitem.namaPegawai
        holder.jabatan.text = currentitem.jabatan
        holder.harga.text = currentitem.total
        holder.tanggal.text = currentitem.tanggal.toString()
    }

    override fun getItemCount(): Int {
        return listClassRiwayat.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.riwayat_tv_nama)
        val jabatan: TextView = itemView.findViewById(R.id.riwayat_tv_jabatan)
        val harga: TextView = itemView.findViewById(R.id.riwayat_tv_harga)
        val tanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
    }
}