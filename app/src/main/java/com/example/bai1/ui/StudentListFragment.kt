package com.example.bai1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bai1.SinhVien
import com.example.bai1.SinhVienAdapter
import com.example.bai1.StudentViewModel
import com.example.bai1.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {

    private lateinit var binding: FragmentStudentListBinding
    private val viewModel: StudentViewModel by activityViewModels()

    private val studentAdapter: SinhVienAdapter by lazy {
        SinhVienAdapter { student -> handleStudentClick(student) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        initializeUI()
        observeStudentList()
    }

    private fun initializeRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = studentAdapter
        }
    }

    private fun initializeUI() {
        binding.fabAdd.setOnClickListener {
            val action = StudentListFragmentDirections.actionStudentListFragmentToAddStudentFragment()
            findNavController().navigate(action)
        }
    }

    private fun observeStudentList() {
        viewModel.students.observe(viewLifecycleOwner) { studentList ->
            // Create a new list copy for the adapter to detect changes
            studentAdapter.submitList(studentList.toList())
        }
    }

    private fun handleStudentClick(student: SinhVien) {
        val action = StudentListFragmentDirections.actionStudentListFragmentToStudentDetailFragment(student.mssv)
        findNavController().navigate(action)
    }
}
