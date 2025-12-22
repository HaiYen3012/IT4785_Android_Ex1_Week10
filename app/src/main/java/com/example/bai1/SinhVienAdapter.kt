package com.example.bai1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bai1.databinding.ItemSinhvienBinding

class SinhVienAdapter(private val onItemClick: (SinhVien) -> Unit) :
    ListAdapter<SinhVien, SinhVienAdapter.SinhVienViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinhVienViewHolder {
        val binding = ItemSinhvienBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SinhVienViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SinhVienViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClick(current)
        }
        holder.bind(current)
    }

    class SinhVienViewHolder(private val binding: ItemSinhvienBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(sinhVien: SinhVien) {
            binding.tvStudentId.text = sinhVien.mssv
            binding.tvStudentName.text = sinhVien.name
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<SinhVien>() {
            override fun areItemsTheSame(oldItem: SinhVien, newItem: SinhVien): Boolean {
                return oldItem.mssv == newItem.mssv
            }

            override fun areContentsTheSame(oldItem: SinhVien, newItem: SinhVien): Boolean {
                return oldItem == newItem
            }
        }
    }
}
