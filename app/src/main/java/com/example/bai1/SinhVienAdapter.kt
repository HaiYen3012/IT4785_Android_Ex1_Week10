package com.example.bai1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bai1.R

class SinhVienAdapter(
    private var students: List<SinhVien>,
    private val onItemClick: (SinhVien) -> Unit
) : RecyclerView.Adapter<SinhVienAdapter.StudentViewHolder>() {

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvId: TextView = view.findViewById(R.id.tvId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sinhvien, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.name
        holder.tvId.text = student.id
        holder.itemView.setOnClickListener { onItemClick(student) }
    }

    override fun getItemCount() = students.size

    fun updateStudents(newStudents: List<SinhVien>) {
        students = newStudents
        notifyDataSetChanged()
    }
}
