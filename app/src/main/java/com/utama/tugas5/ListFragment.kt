package com.utama.tugas5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val rvData = view.findViewById<RecyclerView>(R.id.rvDataHujan)
        rvData.layoutManager = LinearLayoutManager(requireContext())

        val dataHujan = listOf(
            DataHujan(1, "Senin, 21 Apr", "Hujan Lebat", "85 mm"),
            DataHujan(2, "Selasa, 22 Apr", "Gerimis", "20 mm"),
            DataHujan(3, "Rabu, 23 Apr", "Tidak Hujan", "0 mm"),
            DataHujan(4, "Kamis, 24 Apr", "Hujan Sedang", "45 mm"),
            DataHujan(5, "Jumat, 25 Apr", "Hujan Lebat", "90 mm")
        )

        rvData.adapter = DataHujanAdapter(dataHujan)
        return view
    }
}