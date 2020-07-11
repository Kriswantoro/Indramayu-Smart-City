package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.list_tempat.kantor_polisi.KantorPolisiAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.kantor_polisi.KantorPolisiModel
import com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit.RumahSakitAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit.RumahSakitModel

class KantorPolisiActivity : AppCompatActivity() {

    private val mDataList = ArrayList<KantorPolisiModel>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kantor_polisi)

        val list_k_polisi = this.findViewById(R.id.list_k_polisi) as RecyclerView

        list_k_polisi.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,  false)

        setDataListItems()

        val adapter =
            KantorPolisiAdapter(
                mDataList
            )

        list_k_polisi.adapter = adapter
    }

    private fun setDataListItems() {
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
        mDataList.add(
            KantorPolisiModel(
                "POLRES INDRAMAYU",
                "100 KM"
            )
        )
    }
}
