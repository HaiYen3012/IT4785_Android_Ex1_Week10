package com.example.bai1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SinhVienAdapter(
    private val sinhVienList: List<SinhVien>,
    private val onItemClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<SinhVienAdapter.SinhVienViewHolder>() {

    // Lớp ViewHolder để giữ các view của một item
    class SinhVienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHoTen: TextView = itemView.findViewById(R.id.tvHoTen)
        val tvMssv: TextView = itemView.findViewById(R.id.tvMssv)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }

    // Tạo ViewHolder mới (được gọi bởi layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinhVienViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sinhvien, parent, false)
        return SinhVienViewHolder(view)
    }

    // Lấy số lượng item trong danh sách
    override fun getItemCount(): Int {
        return sinhVienList.size
    }

    // Gắn dữ liệu vào ViewHolder (được gọi bởi layout manager)
    override fun onBindViewHolder(holder: SinhVienViewHolder, position: Int) {
        val currentSinhVien = sinhVienList[position]
        holder.tvHoTen.text = currentSinhVien.hoTen
        holder.tvMssv.text = currentSinhVien.mssv

        // Bắt sự kiện click vào cả item
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }

        // Bắt sự kiện click vào icon xóa
        holder.ivDelete.setOnClickListener {
            onDeleteClick(position)
        }
    }
}
