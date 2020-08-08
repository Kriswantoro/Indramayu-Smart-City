package com.kriswantoro.indramayu.ui.beranda.buat_pengaduan

import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import com.squareup.picasso.Picasso
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

        when (holder.itemView.id_diproses.text) {
            "belum diproses" -> {
                holder.itemView.id_diproses.setTextColor(Color.GRAY)
            }
            "sedang diproses" -> {
                holder.itemView.id_diproses.setTextColor(Color.GREEN)
            }
            else -> holder.itemView.id_diproses.setTextColor(Color.RED)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val listPengaduan: TextView = itemView.list_pengaduan
        private val lokasiTempat: TextView = itemView.lokasi_tempat
        private val idDeskripsi: TextView = itemView.id_deskripsi
        private val idKategori: TextView = itemView.id_kategori
        private val idProses: TextView = itemView.id_diproses
        private val photoProfil: ImageView = itemView.foto_profil
        private val photoPengaduan: ImageView = itemView.foto_pengaduan

        fun bindItem(pengaduan: PengaduanModel) {
            listPengaduan.text = pengaduan.judulPengaduan
            idKategori.text = pengaduan.kategori
            lokasiTempat.text = pengaduan.lokasi
            idDeskripsi.text = pengaduan.pesan
            idProses.text = pengaduan.status

            val base64String = pengaduan.fotoPengaduan
            val pureBase64Encoded = base64String.substring(base64String.indexOf(",") + 1)
            val imageBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)


            if (pengaduan.fotoPengguna == "") {
                Picasso.get().load(R.drawable.foto_profile).into(photoProfil)
            } else Picasso.get().load(pengaduan.fotoPengguna).into(photoProfil)
            if (pengaduan.fotoPengaduan == "") {
                Picasso.get().load(R.drawable.gambar).into(photoPengaduan)
            } else photoPengaduan.setImageBitmap(decodedImage)
        }

    }
}