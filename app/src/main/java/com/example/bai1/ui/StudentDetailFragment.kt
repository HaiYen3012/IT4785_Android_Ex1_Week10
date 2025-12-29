package com.example.bai1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bai1.SinhVien
import com.example.bai1.StudentViewModel
import com.example.bai1.databinding.FragmentStudentDetailBinding

class StudentDetailFragment : Fragment() {
    private var _binding: FragmentStudentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUpdate.setOnClickListener { tryUpdateStudent() }
        binding.btnDelete.setOnClickListener { confirmAndDeleteStudent() }
    }

    private fun tryUpdateStudent() {
        val currentStudent = viewModel.selectedStudent.value ?: return

        val id = binding.etStudentId.text.toString().trim()
        val name = binding.etStudentName.text.toString().trim()
        val phone = binding.etStudentPhone.text.toString().trim()
        val address = binding.etStudentAddress.text.toString().trim()

        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(context, "MSSV và Tên không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        if (id != currentStudent.id && viewModel.isStudentIdExists(id)) {
            Toast.makeText(context, "MSSV này đã tồn tại", Toast.LENGTH_SHORT).show()
            binding.etStudentId.requestFocus()
        } else {
            val updatedStudent = SinhVien(id, name, phone, address)
            viewModel.updateStudent(currentStudent.id, updatedStudent)
            Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun confirmAndDeleteStudent() {
        val student = viewModel.selectedStudent.value ?: return

        AlertDialog.Builder(requireContext())
            .setTitle("Xác nhận Xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                viewModel.deleteStudent(student.id)
                Toast.makeText(context, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearSelectedStudent()
        _binding = null
    }
}
