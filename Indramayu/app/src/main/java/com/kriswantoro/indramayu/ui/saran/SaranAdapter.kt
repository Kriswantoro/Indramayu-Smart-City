package com.kriswantoro.indramayu.ui.saran

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_pengaduan.view.*
import kotlinx.android.synthetic.main.item_list_pengaduan.view.*
import kotlinx.android.synthetic.main.item_list_pengaduan.view.foto_pengaduan
import kotlinx.android.synthetic.main.item_list_saran.view.*

class SaranAdapter(val list : ArrayList<SaranModel>) : RecyclerView.Adapter<SaranAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_saran, parent, false)

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: SaranModel = list[position]

        holder.cv_judul_saran.text = user.cv_judul_saran
        holder.cv_deskripsi.text = user.cv_deskripsi
//        holder.cv_tanggal_saran.text = user.cv_tanggal_saran
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val cv_judul_saran = itemView.cv_judul_saran
        val cv_deskripsi = itemView.cv_deskripsi
//        val cv_tanggal_saran = itemView.cv_tanggal_saran


    }
}