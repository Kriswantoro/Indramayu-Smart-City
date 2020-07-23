package com.kriswantoro.indramayu.ui.tempat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.list_tempat.ibadah.IbadahActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.kantor_polisi.KantorPolisiActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit.RumahSakitActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.wisata.WisataActivity
import kotlinx.android.synthetic.main.fragment_tempat.view.*

class TempatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tempat, container, false)
        root.ibadah.setOnClickListener {
            val intent = Intent(context, IbadahActivity::class.java)
            intent.putExtra("id_kategori", 1)
            startActivity(intent)
        }
        root.rumah_sakit.setOnClickListener {
            val intent = Intent(context, RumahSakitActivity::class.java)
            intent.putExtra("id_kategori", 4)
            startActivity(intent)
        }
        root.ktr_polisi.setOnClickListener {
            val intent = Intent(context, KantorPolisiActivity::class.java)
            intent.putExtra("id_kategori", 3)
            startActivity(intent)
        }
        root.wisata.setOnClickListener {
            val intent = Intent(context, WisataActivity::class.java)
            intent.putExtra("id_kategori", 2)
            startActivity(intent)
        }
        return root
    }
}
