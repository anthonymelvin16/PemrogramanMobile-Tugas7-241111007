package com.utama.tugas5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataHujanAdapter(
    private val listData: List<DataHujanApi>,
    private val onClick: (DataHujanApi) -> Unit
) : RecyclerView.Adapter<DataHujanAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tvKondisi: TextView = itemView.findViewById(R.id.tvKondisi)
        val tvCurah  : TextView = itemView.findViewById(R.id.tvCurah)
        val btnEdit  : Button   = itemView.findViewById(R.id.btnEdit)
        val btnHapus : Button   = itemView.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data_hujan, parent, false)
        return ViewHolder(itemView = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.tvTanggal.text = data.tanggal
        holder.tvKondisi.text = data.kategori
        holder.tvCurah.text   = "Curah: ${data.curahHujan} mm"
        holder.btnEdit.setOnClickListener  { onClick(data) }
        holder.btnHapus.setOnClickListener { onClick(data) }
    }

    override fun getItemCount(): Int = listData.size
}