package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.activity_pengaduan.view.*
import kotlinx.android.synthetic.main.item_list_pengaduan.view.*

class PengaduanAdapter(val list : ArrayList<PengaduanModel>) : RecyclerView.Adapter<PengaduanAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_pengaduan, parent, false)

        return ViewHolder(
            v
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: PengaduanModel = list[position]

        holder.list_pengaduan.text = user.list_pengaduan
        holder.lokasi_tempat.text = user.lokasi_tempat
        holder.id_deskripsi.text = user.id_deskripsi
        holder.id_diproses.text = user.id_diproses
//        holder.foto_pengaduan.imageAlpha
//        holder.foto_profil.imageAlpha
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val list_pengaduan = itemView.list_pengaduan
        val lokasi_tempat = itemView.lokasi_tempat
        val id_deskripsi = itemView.id_deskripsi
        val id_diproses = itemView.id_diproses
//        val foto_pengaduan = itemView.foto_pengaduan!!
//        val foto_profil = itemView.foto_profil!!


    }
}