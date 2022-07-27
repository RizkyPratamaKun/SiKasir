package com.project.sikasir.produk.pembelian

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterInformasiPembelian(private val listProduk: ArrayList<classPembelian>) :
        RecyclerView.Adapter<adapterInformasiPembelian.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_pembelian, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = listProduk[position]

        val Nama_Produk = currentitem.namaProduk
        val Tanggal = currentitem.tanggal
        val jumlah = currentitem.jumlah_Produk
        val harga = currentitem.harga_Modal
        val beli = currentitem.totalPembelian
        val ket = currentitem.keterangan
        val NPegawai = currentitem.pegawai

        holder.Nama.text = Nama_Produk
        holder.Harga.text = harga
        holder.Tanggal.text = Tanggal
        holder.total.text = beli
        holder.jumlah.text = jumlah
        holder.np.text = NPegawai
    }

    override fun getItemCount(): Int {
        return listProduk.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val Nama: TextView = itemView.findViewById(R.id.kp_tv_namalist)
        val Harga: TextView = itemView.findViewById(R.id.tv_hargamodal_pembelian)
        val Tanggal: TextView = itemView.findViewById(R.id.tv_tanggal_pembelian)
        val jumlah: TextView = itemView.findViewById(R.id.tv_jumlah_beli)
        val total: TextView = itemView.findViewById(R.id.tv_total_pembelian)
        val np: TextView = itemView.findViewById(R.id.tv_nama_pembeli)

    }

}