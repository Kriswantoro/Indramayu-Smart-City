package com.kriswantoro.indramayu.ui.beranda.panggilan_darurat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.util.retrofit.model.NomorPanggilanModel
import com.kriswantoro.indramayu.util.retrofit.network.BaseService
import com.kriswantoro.indramayu.util.retrofit.network.response.BaseResponse
import kotlinx.android.synthetic.main.activity_panggilan_darurat.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PanggilanDaruratActivity : AppCompatActivity() {

    var polisi: String = ""
    var rsud_indramayu: String = ""
    var rsud_sentot: String = ""
    var rsud_mis: String = ""
    var pemadam: String = ""

    val mListNomor: ArrayList<NomorPanggilanModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panggilan_darurat)

        getNomor()
        btn_ktr_polisi.setOnClickListener {
            //Dialer intent
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(polisi)))
            startActivity(intent)
        }

        btn_rsud_indramayu.setOnClickListener {
            //Dialer intent
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(rsud_indramayu)))
            startActivity(intent)
        }
        btn_rsud_sentot.setOnClickListener {
            //Dialer intent
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(rsud_sentot)))
            startActivity(intent)
        }
        btn_rsud_MIS.setOnClickListener {
            //Dialer intent
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(rsud_mis)))
            startActivity(intent)
        }
        btn_p_kebakaran.setOnClickListener {
            //Dialer intent
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(pemadam)))
            startActivity(intent)
        }
    }

    fun getNomor() {
        BaseService.getNomorPanggilan().getNomorPanggilan("getpanggilan")
            .enqueue(object : Callback<BaseResponse<NomorPanggilanModel>> {
                override fun onFailure(
                    call: Call<BaseResponse<NomorPanggilanModel>>,
                    t: Throwable
                ) {
                    //nothing
                }

                override fun onResponse(
                    call: Call<BaseResponse<NomorPanggilanModel>>,
                    response: Response<BaseResponse<NomorPanggilanModel>>
                ) {
                    val responseData = response.body()?.response
                    if (response.body()?.code == 200) {
                        val data = Gson().toJson(responseData)
                        mListNomor.addAll(responseData!!.toList())

                        val size = mListNomor.size

                        for (i in 0 until size) {
                            when (mListNomor[i].idPanggilan) {
                                1 -> {
                                    polisi = mListNomor[i].nomorPanggilan
                                }
                                2 -> {
                                    rsud_indramayu = mListNomor[i].nomorPanggilan
                                }
                                4 -> {
                                    rsud_sentot = mListNomor[i].nomorPanggilan
                                }
                                5 -> {
                                    rsud_mis = mListNomor[i].nomorPanggilan
                                }
                                else -> pemadam = mListNomor[i].nomorPanggilan
                            }
                        }

                        Log.e("listNomor", data.toString())
                    }
                }

            })
    }
}
