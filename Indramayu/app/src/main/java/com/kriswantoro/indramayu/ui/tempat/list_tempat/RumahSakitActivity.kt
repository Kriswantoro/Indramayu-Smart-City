package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit.RumahSakitAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit.RumahSakitModel

class RumahSakitActivity : AppCompatActivity() {

    private val mDataList = ArrayList<RumahSakitModel>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rumah_sakit)

        val list_r_sakit = this.findViewById(R.id.list_r_sakit) as RecyclerView

        list_r_sakit.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,  false)

        setDataListItems()

        val adapter =
            RumahSakitAdapter(
                mDataList
            )

        list_r_sakit.adapter = adapter
    }

    private fun setDataListItems() {
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
        mDataList.add(
            RumahSakitModel(
                "RSUD INDRAMAYU",
                "50 KM"
            )
        )
    }
}
