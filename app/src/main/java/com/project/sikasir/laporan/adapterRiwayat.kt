package com.project.sikasir.laporan

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

        holder.subtotal.text = currentitem.subtotal
        holder.kd_riwayat.text = currentitem.kode_riwayat
        holder.tanggal.text = currentitem.tanggal
    }

    override fun getItemCount(): Int {
        return listClassRiwayat.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subtotal: TextView = itemView.findViewById(R.id.tv_subtotal)
        val kd_riwayat: TextView = itemView.findViewById(R.id.kd_riwayat)
        val status: TextView = itemView.findViewById(R.id.status_transaksi)
        val tanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
    }
}