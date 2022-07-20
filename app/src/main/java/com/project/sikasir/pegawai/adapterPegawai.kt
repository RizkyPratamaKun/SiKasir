package com.project.sikasir.pegawai

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.R
import com.project.sikasir.listview.classPegawai
import com.squareup.picasso.Picasso

/**
 * Dibuat oleh RizkyPratama pada 08-May-22.
 */
class adapterPegawai(private val listPegawai: ArrayList<classPegawai>) :
        RecyclerView.Adapter<adapterPegawai.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_pegawai,
            parent, false
        )
        return MyViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return listPegawai.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView = itemView.findViewById(R.id.tv_listnama)
        val Banner: TextView = itemView.findViewById(R.id.tv_nmjabatan)
        val Jabatan: TextView = itemView.findViewById(R.id.tv_l_jabatan)
        val Foto: ImageView = itemView.findViewById(R.id.iv_detail_pegawai)
        val FotoKosong: ImageView = itemView.findViewById(R.id.iv_kosong_pegawai)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = listPegawai[position]

        holder.Nama.text = currentitem.Nama_Pegawai
        holder.Jabatan.text = currentitem.Nama_Jabatan

        if (currentitem.Foto.isNullOrEmpty()) {
            holder.FotoKosong.visibility = View.VISIBLE
        } else {
            holder.FotoKosong.visibility = View.GONE
            Picasso.get().load(currentitem.Foto).centerCrop().fit().into(holder.Foto)
        }

        if (currentitem.Hak_Akses == "Operator") {
            holder.Banner.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorRecyclerViewDelete))
            holder.Banner.text = "OP"
        } else {
            holder.Banner.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.blue))
            holder.Banner.text = "SP"
        }

        holder.itemView.setOnClickListener {
            //From This
            val Nama_Pegawai = listPegawai[position].Nama_Pegawai
            val Nama_Jabatan = listPegawai[position].Nama_Jabatan
            val Hak_Akses = listPegawai[position].Hak_Akses
            val HP = listPegawai[position].HP
            val Pin = listPegawai[position].Pin
            val Email = listPegawai[position].Email_Pegawai
            val Foto = listPegawai[position].Foto


            //To This
            val intent = Intent(holder.itemView.context, kelolaPegawai::class.java)
            intent.putExtra("Nama_Pegawai", Nama_Pegawai)
            intent.putExtra("Nama_Jabatan", Nama_Jabatan)
            intent.putExtra("Hak_Akses", Hak_Akses)
            intent.putExtra("HP", HP)
            intent.putExtra("Email", Email)
            intent.putExtra("Pin", Pin)
            intent.putExtra("Foto", Foto)
            intent.putExtra("Edit", "true")

            holder.itemView.context.startActivity(intent)
        }
    }
}