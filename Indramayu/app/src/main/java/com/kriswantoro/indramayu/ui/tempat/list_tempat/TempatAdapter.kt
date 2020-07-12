package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_tempat.view.*
import kotlinx.android.synthetic.main.item_list_tempat.view.nama_tempat

class TempatAdapter(val list: ArrayList<TempatModel>, val listener: (TempatModel) -> Unit) :
    RecyclerView.Adapter<TempatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_tempat, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(list[position], listener)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //biasai nama variable lowerUpper
//        val nama_tempat: TextView = itemView.nama_tempat
        val namaTempat: TextView = itemView.nama_tempat
        val fotoTempat: ImageView = itemView.foto_tempat

        fun bindItem(user: TempatModel, listener: (TempatModel) -> Unit) {
            Picasso.get().load(user.fotoTempat).into(fotoTempat)
            namaTempat.text = user.namaTempat
            itemView.setOnClickListener { listener(user) }
        }
    }
}