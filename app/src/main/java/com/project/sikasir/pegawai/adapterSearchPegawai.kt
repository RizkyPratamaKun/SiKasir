package com.project.sikasir.pegawai

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.sikasir.databinding.ListPegawaiBinding
import com.project.sikasir.listview.classPegawai

class adapterSearchPegawai(val listPegawai: ArrayList<classPegawai>) : ListAdapter<classPegawai, adapterSearchPegawai.MyHolder>(COMPARATOR) {

    private object COMPARATOR : DiffUtil.ItemCallback<classPegawai>() {
        override fun areItemsTheSame(oldItem: classPegawai, newItem: classPegawai): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: classPegawai, newItem: classPegawai): Boolean {
            return oldItem.Nama_Pegawai == newItem.Nama_Pegawai
        }
    }

    // An innerclass that maps data with the available views
    inner class MyHolder(private val binding: ListPegawaiBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(produk: classPegawai?) {
            binding.tvListnama.text = produk?.Nama_Pegawai.toString()
            binding.tvNmjabatan.text = produk?.Nama_Jabatan.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ListPegawaiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val currentitem = listPegawai[position]

        holder.itemView.setOnClickListener {
            println(currentitem.Nama_Pegawai)
        }
    }
}