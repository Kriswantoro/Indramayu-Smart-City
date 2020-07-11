package com.kriswantoro.indramayu.ui.tempat.list_tempat.kantor_polisi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.item_list_kantor_polisi.view.*
import kotlinx.android.synthetic.main.item_list_rumah_sakit.view.*

class KantorPolisiAdapter(val list : ArrayList<KantorPolisiModel>) : RecyclerView.Adapter<KantorPolisiAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_kantor_polisi, parent, false)

        return ViewHolder(
            v
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: KantorPolisiModel = list[position]

        holder.nama_k_polisi.text = user.nama_k_polisi
        holder.jarak_k_polisi.text = user.jarak_k_polisi
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nama_k_polisi = itemView.nama_k_polisi
        val jarak_k_polisi = itemView.jarak_k_polisi
//        val foto_k_polisi = itemView.foto_k_polisi!!


    }
}