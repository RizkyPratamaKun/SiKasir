package com.project.sikasir.laporan.pegawai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R
import com.project.sikasir.listview.classPegawai

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterLaporanPegawai(private val listPegawai: ArrayList<classPegawai>) :
        RecyclerView.Adapter<adapterLaporanPegawai.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.laporan_pegawai, parent, false)

        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return listPegawai.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView = itemView.findViewById(R.id.tv_l_nama)
        val Jabatan: TextView = itemView.findViewById(R.id.tv_l_jabatan)
        val transaksi: TextView = itemView.findViewById(R.id.tv_l_total)
        val jumlah: TextView = itemView.findViewById(R.id.tv_l_jml)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = listPegawai[position]

        holder.Nama.text = currentitem.Nama_Pegawai
        holder.Jabatan.text = currentitem.Nama_Jabatan
        holder.transaksi.text = currentitem.Nama_Jabatan
        holder.jumlah.text = currentitem.Nama_Jabatan

    }
}