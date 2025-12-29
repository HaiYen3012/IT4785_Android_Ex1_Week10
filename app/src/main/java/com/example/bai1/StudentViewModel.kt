package com.example.bai1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val dataSource = StudentDataSource(application)

    private val _students = MutableLiveData<List<SinhVien>>()
    val students: LiveData<List<SinhVien>> = _students

    private val _selectedStudent = MutableLiveData<SinhVien?>()
    val selectedStudent: LiveData<SinhVien?> = _selectedStudent

    init {
        dataSource.open()
        val currentStudents = dataSource.retrieveAllStudents()
        if (currentStudents.isEmpty()) {
            dataSource.createStudent(SinhVien("20215432", "Trần Minh Quang", "0988123456", "Hà Nội"))
            dataSource.createStudent(SinhVien("20216789", "Nguyễn Thu Hằng", "0911223344", "Đà Nẵng"))
            dataSource.createStudent(SinhVien("20219876", "Lý Gia Hân", "0905678123", "TP. Hồ Chí Minh"))
        }
        refreshList()
    }

    private fun refreshList() {
        _students.value = dataSource.retrieveAllStudents()
    }

    fun addStudent(student: SinhVien) {
        dataSource.createStudent(student)
        refreshList()
    }

    fun updateStudent(originalId: String, updatedStudent: SinhVien) {
        dataSource.updateStudent(originalId, updatedStudent)
        refreshList()
    }

    fun deleteStudent(studentId: String) {
        dataSource.removeStudent(studentId)
        refreshList()
    }

    fun isStudentIdExists(studentId: String): Boolean {
        return dataSource.isIdTaken(studentId)
    }

    fun selectStudent(student: SinhVien) {
        _selectedStudent.value = student
    }

    fun clearSelectedStudent() {
        _selectedStudent.value = null
    }

    override fun onCleared() {
        dataSource.close()
        super.onCleared()
    }
}
