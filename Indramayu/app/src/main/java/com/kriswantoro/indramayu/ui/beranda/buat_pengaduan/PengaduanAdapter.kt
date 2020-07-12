package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.item_list_pengaduan.view.*

class PengaduanAdapter(val list: ArrayList<PengaduanModel>) :
    RecyclerView.Adapter<PengaduanAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_pengaduan, parent, false)
        return ViewHolder(v)

    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(list[position])

        if (holder.itemView.id_diproses.text == "Sedang Diproses") {
            holder.itemView.id_diproses.setTextColor(Color.RED)
        } else {
            holder.itemView.id_diproses.setTextColor(Color.GREEN)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val listPengaduan: TextView = itemView.list_pengaduan
        private val lokasiTempat: TextView = itemView.lokasi_tempat
        private val idDeskripsi: TextView = itemView.id_deskripsi
        private val idKategori: TextView = itemView.id_kategori
        private val idProses: TextView = itemView.id_diproses

        fun bindItem(pengaduan: PengaduanModel) {
            listPengaduan.text = pengaduan.judulPengaduan
            idKategori.text = pengaduan.kategori
            lokasiTempat.text = pengaduan.lokasi_pengaduan
            idDeskripsi.text = pengaduan.pesan
            idProses.text = pengaduan.status
        }

    }
}