package com.kriswantoro.indramayu.ui.tempat.list_tempat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kriswantoro.indramayu.R
import com.kriswantoro.indramayu.ui.tempat.list_tempat.ibadah.IbadahAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.ibadah.IbadahModel
import com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit.RumahSakitAdapter
import com.kriswantoro.indramayu.ui.tempat.list_tempat.rumah_sakit.RumahSakitModel

class IbadahActivity : AppCompatActivity() {

    private val mDataList = ArrayList<IbadahModel>()

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ibadah)

        val list_ibadah = this.findViewById(R.id.list_ibadah) as RecyclerView

        list_ibadah.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL,  false)

        setDataListItems()

        val adapter =
            IbadahAdapter(
                mDataList
            )

        list_ibadah.adapter = adapter
    }

    private fun setDataListItems() {
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
        mDataList.add(
            IbadahModel(
                "Masjid Jamie Darussalam",
                "10 KM"
            )
        )
    }
}
