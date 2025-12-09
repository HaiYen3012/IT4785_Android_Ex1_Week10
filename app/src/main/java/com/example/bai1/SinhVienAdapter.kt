package com.example.bai1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SinhVienAdapter(
    private val sinhVienList: List<SinhVien>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder>() {

    class SinhVienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHoTen: TextView = itemView.findViewById(R.id.tvHoTen)
        val tvMssv: TextView = itemView.findViewById(R.id.tvMssv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinhVienViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sinhvien, parent, false)
        return SinhVienViewHolder(view)
    }

    override fun getItemCount(): Int {
        return sinhVienList.size
    }

    override fun onBindViewHolder(holder: SinhVienViewHolder, position: Int) {
        val currentSinhVien = sinhVienList[position]
        holder.tvHoTen.text = currentSinhVien.hoTen
        holder.tvMssv.text = currentSinhVien.mssv

        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }
}