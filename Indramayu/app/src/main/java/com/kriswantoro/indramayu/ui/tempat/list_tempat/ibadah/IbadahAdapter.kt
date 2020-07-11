package com.kriswantoro.indramayu.ui.tempat.list_tempat.ibadah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.item_list_ibadah.view.*
import kotlinx.android.synthetic.main.item_list_kantor_polisi.view.*

class IbadahAdapter(val list : ArrayList<IbadahModel>) : RecyclerView.Adapter<IbadahAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_ibadah, parent, false)

        return ViewHolder(
            v
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: IbadahModel = list[position]

        holder.nama_ibadah.text = user.nama_ibadah
        holder.jarak_ibadah.text = user.jarak_ibadah
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nama_ibadah = itemView.nama_ibadah
        val jarak_ibadah = itemView.jarak_ibadah
//        val foto_k_polisi = itemView.foto_k_polisi!!


    }
}