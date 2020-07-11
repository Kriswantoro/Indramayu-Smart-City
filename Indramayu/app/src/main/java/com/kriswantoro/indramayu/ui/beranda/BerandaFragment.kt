package com.kriswantoro.indramayu.ui.beranda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.sample.setDrawableImage
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanActivity
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanAdapter
import com.kriswantoro.indramayu.ui.beranda.buat_pengaduan.PengaduanModel
import com.kriswantoro.indramayu.ui.beranda.panggilan_darurat.PanggilanDaruratActivity
import kotlinx.android.synthetic.main.fragment_beranda.view.*
import kotlinx.android.synthetic.main.fragment_beranda.view.foto_profil

class BerandaFragment : Fragment() {

    private val mDataList = ArrayList<PengaduanModel>()

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_beranda, container, false)
        root.btn_pengaduan.setOnClickListener {
            startActivity(Intent(context, PengaduanActivity::class.java))
        }
        root.btn_darurat.setOnClickListener {
            startActivity(Intent(context, PanggilanDaruratActivity::class.java))
        }

        root.foto_profil.setDrawableImage(R.drawable.foto_profile, true)

        val list_pengaduan = root.findViewById(R.id.list_pengaduan) as RecyclerView

        list_pengaduan.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL,  false)

        setDataListItems()

        val adapter =
            PengaduanAdapter(
                mDataList
            )

        list_pengaduan.adapter = adapter

        return root
    }



    private fun setDataListItems() {
        mDataList.add(
            PengaduanModel(
                "Jalan Rusak",
                "Lelea Kec. Lelea Kab. Indramayu",
                "Sedang Diproses",
                "Jalanan Rusak Di Jalan Raya Lelea"
            )
        )
        mDataList.add(
            PengaduanModel(
                "Jalan Rusak",
                "Lelea Kec. Lelea Kab. Indramayu",
                "Sedang Diproses",
                "Jalanan Rusak Di Jalan Raya Lelea"
            )
        )
        mDataList.add(
            PengaduanModel(
                "Jalan Rusak",
                "Lelea Kec. Lelea Kab. Indramayu",
                "Sedang Diproses",
                "Jalanan Rusak Di Jalan Raya Lelea"
            )
        )
        mDataList.add(
            PengaduanModel(
                "Jalan Rusak",
                "Lelea Kec. Lelea Kab. Indramayu",
                "Sedang Diproses",
                "Jalanan Rusak Di Jalan Raya Lelea"
            )
        )
    }
}
