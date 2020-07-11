package com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.item_list_kantor_polisi.view.*
import kotlinx.android.synthetic.main.item_list_wisata.view.*

class WisataAdapter(val list : ArrayList<WisataModel>) : RecyclerView.Adapter<WisataAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_list_wisata, parent, false)

        return ViewHolder(
            v
        )

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user: WisataModel = list[position]

        holder.nama_tempat.text = user.nama_tempat
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nama_tempat = itemView.nama_tempat

    }
}