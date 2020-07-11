package com.kriswantoro.indramayu.ui.akun

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dhaval2404.imagepicker.sample.setDrawableImage

import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.akun.edit_akun.EditAkunAktivity
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import kotlinx.android.synthetic.main.activity_edit_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*
import kotlinx.android.synthetic.main.fragment_akun.view.foto_profil

class AkunFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_akun, container, false)

        root.btn_keluar.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
        root.edit_profil.setOnClickListener {
            startActivity(Intent(context, EditAkunAktivity::class.java))
        }
        root.tentang_apk.setOnClickListener {
            startActivity(Intent(context, TentangISCActivity::class.java))
        }

        root.foto_profil.setDrawableImage(R.drawable.foto_profile, true)

//        Picasso.get()
//            .load("https://non-indonesia-distribution.brta.in/2018-08/ce8014b02db258d883f545cc27bf4b35.jpg")
//            .into(root.foto_profil)

        return root
    }

}
