package com.kriswantoro.indramayu.ui.akun

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.imagepicker.sample.setDrawableImage
import com.kriswantoro.indramayu.MainActivity

import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.intro.SharedPref
import com.kriswantoro.indramayu.ui.akun.edit_akun.EditAkunAktivity
import com.kriswantoro.indramayu.verifikasi.LoginActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_akun.*
import kotlinx.android.synthetic.main.fragment_akun.view.*
import kotlinx.android.synthetic.main.fragment_akun.view.foto_profil

class AkunFragment : Fragment() {

    private lateinit var namaPengguna: TextView
    private lateinit var nomorPengguna: TextView
    private lateinit var fotoPengguna: com.mikhaellopez.circularimageview.CircularImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_akun, container, false)

        if (SharedPref.getInstance(requireContext()).isLoggedIn) {

            namaPengguna = root.findViewById(R.id.nama_pengguna)
            nomorPengguna = root.findViewById(R.id.nomor_pengguna)
            fotoPengguna = root.findViewById(R.id.foto_profil)

            val user = SharedPref.getInstance(requireContext()).user

            namaPengguna.text = user.namaPengguna
            nomorPengguna.text = user.noTlpn
            if (user.fotoPengguna == "") {
                Picasso.get().load(R.drawable.foto_profile).into(fotoPengguna)
            } else Picasso.get().load(user.fotoPengguna).into(fotoPengguna)
        } else {
            Toast.makeText(requireContext(), "You're not Loggedin", Toast.LENGTH_LONG).show()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        root.btn_keluar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Alert!")
                .setMessage("Apakah anda yakin ingin logout ?")
                .setPositiveButton("Ya") { _, _ ->
                    SharedPref.getInstance(requireContext()).userLogout()
                    startActivity(Intent(context, LoginActivity::class.java))
                    onDestroy()
                }
                .setNegativeButton("Tidak") { _, _ -> }
                .show()
        }
        root.edit_profil.setOnClickListener {
            startActivity(Intent(context, EditAkunAktivity::class.java))
        }
//        root.tentang_apk.setOnClickListener {
//            startActivity(Intent(context, TentangISCActivity::class.java))
//        }

//        root.foto_profil.setDrawableImage(R.drawable.foto_profile, true)

//        Picasso.get()
//            .load("https://non-indonesia-distribution.brta.in/2018-08/ce8014b02db258d883f545cc27bf4b35.jpg")
//            .into(root.foto_profil)

        return root
    }

}
