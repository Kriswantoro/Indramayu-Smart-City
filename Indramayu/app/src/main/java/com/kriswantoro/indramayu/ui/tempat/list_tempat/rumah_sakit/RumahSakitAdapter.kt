package com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.item_list_pengaduan.view.*
import kotlinx.android.synthetic.main.item_list_rumah_sakit.view.*

class RumahSakitAdapter(val list : ArrayList<RumahSakitModel>) : RecyclerView.Adapter<RumahSakitAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_rumah_sakit, parent, false)

        return ViewHolder(
            v
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: RumahSakitModel = list[position]

        holder.nama_r_sakit.text = user.nama_r_sakit
        holder.jarak_r_sakit.text = user.jarak_r_sakit
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nama_r_sakit = itemView.nama_r_sakit
        val jarak_r_sakit = itemView.jarak_r_sakit
//        val foto_r_sakit = itemView.foto_r_sakit!!


    }
}