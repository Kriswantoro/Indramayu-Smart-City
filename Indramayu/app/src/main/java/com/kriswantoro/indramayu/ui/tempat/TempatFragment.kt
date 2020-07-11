package com.kriswantoro.indramayu.ui.tempat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.list_tempat.IbadahActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.KantorPolisiActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.RumahSakitActivity
import com.kriswantoro.indramayu.ui.tempat.list_tempat.WisataActivity
import kotlinx.android.synthetic.main.fragment_tempat.view.*

class TempatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_tempat, container, false)
        root.ibadah.setOnClickListener {
            startActivity(Intent(context, IbadahActivity::class.java))
        }
        root.rumah_sakit.setOnClickListener {
            startActivity(Intent(context, RumahSakitActivity::class.java))
        }
        root.ktr_polisi.setOnClickListener {
            startActivity(Intent(context, KantorPolisiActivity::class.java))
        }
        root.wisata.setOnClickListener {
            startActivity(Intent(context, WisataActivity::class.java))
        }
        return root
    }
}
