package com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kriswantoro.indramayu.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list_kantor_polisi.view.*
import kotlinx.android.synthetic.main.item_list_wisata.view.*

class WisataAdapter(val list: ArrayList<WisataModel>, val listener: (WisataModel) -> Unit) :
    RecyclerView.Adapter<WisataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_wisata, parent, false)

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
        val fotoWisata: ImageView = itemView.foto_wisata

        fun bindItem(user: WisataModel, listener: (WisataModel) -> Unit) {
            Picasso.get().load(user.foto_wisata).into(fotoWisata)
            namaTempat.text = user.nama_tempat
            itemView.setOnClickListener { listener(user) }
        }
    }
}