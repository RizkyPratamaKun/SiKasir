package com.project.sikasir.transaksi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */

class adapterRiwayat(private val listClassRiwayat: ArrayList<classRiwayat>) :
        RecyclerView.Adapter<adapterRiwayat.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_riwayat,
            parent, false
        )
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listClassRiwayat[position]

        //Holder Dalam listClassRiwayat
        holder.subtotal.text = currentitem.Subtotal
        holder.kd_riwayat.text = currentitem.Riwayat
        holder.Pembayaran.text = currentitem.Pembayaran
        holder.tanggal.text = currentitem.Tanggal
    }

    override fun getItemCount(): Int {
        return listClassRiwayat.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //memilih textview untuk dipasangkan
        val subtotal: TextView = itemView.findViewById(R.id.tv_subtotal)
        val kd_riwayat: TextView = itemView.findViewById(R.id.kd_riwayat)
        val Pembayaran: TextView = itemView.findViewById(R.id.tv_namapegawai)
        val tanggal: TextView = itemView.findViewById(R.id.tv_tanggal)
    }

}