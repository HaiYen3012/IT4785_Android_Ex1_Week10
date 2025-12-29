package com.example.bai1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bai1.SinhVien
import com.example.bai1.StudentViewModel
import com.example.bai1.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {
    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener { trySaveStudent() }
    }

    private fun trySaveStudent() {
        val id = binding.etStudentId.text.toString().trim()
        val name = binding.etStudentName.text.toString().trim()
        val phone = binding.etStudentPhone.text.toString().trim()
        val address = binding.etStudentAddress.text.toString().trim()

        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(context, "MSSV và Tên không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        if (viewModel.isStudentIdExists(id)) {
            Toast.makeText(context, "MSSV này đã tồn tại", Toast.LENGTH_SHORT).show()
            binding.etStudentId.requestFocus()
        } else {
            val student = SinhVien(id, name, phone, address)
            viewModel.addStudent(student)
            Toast.makeText(context, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
