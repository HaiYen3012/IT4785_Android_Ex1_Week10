package com.example.bai1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bai1.R
import com.example.bai1.SinhVienAdapter
import com.example.bai1.StudentViewModel
import com.example.bai1.databinding.FragmentStudentListBinding

class StudentListFragment : Fragment() {
    private var _binding: FragmentStudentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var studentAdapter: SinhVienAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUi()
        observeViewModel()
    }

    private fun initializeUi() {
        studentAdapter = SinhVienAdapter(emptyList()) { student ->
            viewModel.selectStudent(student)
            findNavController().navigate(R.id.action_studentListFragment_to_studentDetailFragment)
        }
        binding.recyclerView.apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_addStudentFragment)
        }
    }

    private fun observeViewModel() {
        viewModel.students.observe(viewLifecycleOwner) { students ->
            studentAdapter.updateStudents(students)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
