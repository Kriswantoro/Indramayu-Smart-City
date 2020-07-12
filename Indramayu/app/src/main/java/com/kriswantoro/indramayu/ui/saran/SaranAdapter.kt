package com.kriswantoro.indramayu.ui.saran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.item_list_saran.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class SaranAdapter(val list : ArrayList<SaranModel>) : RecyclerView.Adapter<SaranAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_saran, parent, false)

        return ViewHolder(v)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(list[position])
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val judulSaran: TextView = itemView.cv_judul_saran
        private val desc: TextView = itemView.cv_deskripsi
        private val tglSaran: TextView = itemView.cv_tanggal_saran

        fun bindItem(saran: SaranModel) {
            judulSaran.text = saran.judulSaran
            desc.text = saran.descSaran

            val tgl = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .parse(saran.tglSaran)
            val format = SimpleDateFormat("dd MMMM yyy", Locale.getDefault())
                .format(tgl!!)

            tglSaran.text = format
        }
    }
}