package com.example.bai1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private val _students = MutableLiveData<MutableList<SinhVien>>(
        mutableListOf(
            SinhVien("20200001", "Nguyễn Văn A", "0123456789", "Hà Nội"),
            SinhVien("20200002", "Trần Thị B", "0987654321", "Hồ Chí Minh"),
            SinhVien("20200003", "Lê Văn C", "0112233445", "Đà Nẵng")
        )
    )
    val students: LiveData<MutableList<SinhVien>> = _students

    private val _selectedStudent = MutableLiveData<SinhVien?>()
    val selectedStudent: LiveData<SinhVien?> = _selectedStudent

    // LiveData for form fields
    val mssv = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val address = MutableLiveData<String>()


    val navigationEvent = MutableLiveData<Event<Unit>>()
    val toastMessage = MutableLiveData<Event<String>>()

    fun saveNewStudent() {
        val studentId = mssv.value
        val studentName = name.value
        val studentPhone = phone.value
        val studentAddress = address.value

        if (studentName.isNullOrEmpty()) {
            toastMessage.value = Event("Vui lòng nhập họ tên")
            return
        }
        if (studentId.isNullOrEmpty()) {
            toastMessage.value = Event("Vui lòng nhập MSSV")
            return
        }
        if (isStudentIdExists(studentId)) {
            toastMessage.value = Event("MSSV đã tồn tại")
            return
        }

        val newStudent = SinhVien(studentId, studentName, studentPhone ?: "", studentAddress ?: "")
        val currentList = _students.value ?: mutableListOf()
        currentList.add(newStudent)
        _students.value = currentList
        toastMessage.value = Event("Đã thêm sinh viên thành công")
        navigationEvent.value = Event(Unit)
    }

    fun updateStudent() {
        val currentStudent = _selectedStudent.value ?: return
        val newId = mssv.value
        val newName = name.value
        val newPhone = phone.value
        val newAddress = address.value

        if (newName.isNullOrEmpty()) {
            toastMessage.value = Event("Vui lòng nhập họ tên")
            return
        }
        if (newId.isNullOrEmpty()) {
            toastMessage.value = Event("Vui lòng nhập MSSV")
            return
        }
        if (newId != currentStudent.mssv && isStudentIdExists(newId)) {
            toastMessage.value = Event("MSSV đã tồn tại")
            return
        }

        val studentList = _students.value?.toMutableList() ?: return
        val index = studentList.indexOfFirst { it.mssv == currentStudent.mssv }
        if (index != -1) {
            val updatedStudent = SinhVien(newId, newName, newPhone ?: "", newAddress ?: "")
            studentList[index] = updatedStudent
            _students.value = studentList
            toastMessage.value = Event("Đã cập nhật sinh viên thành công")
            navigationEvent.value = Event(Unit) // Gửi sự kiện điều hướng
        }
    }

    fun deleteStudent() {
        val studentToDelete = _selectedStudent.value ?: return
        val studentList = _students.value?.toMutableList() ?: return
        studentList.removeAll { it.mssv == studentToDelete.mssv }
        _students.value = studentList
        toastMessage.value = Event("Đã xóa sinh viên")
        navigationEvent.value = Event(Unit) // Gửi sự kiện điều hướng
    }

    fun isStudentIdExists(studentId: String): Boolean {
        return _students.value?.any { it.mssv == studentId } ?: false
    }

    fun initStudentForEdit(student: SinhVien) {
        _selectedStudent.value = student
        mssv.value = student.mssv
        name.value = student.name
        phone.value = student.phone
        address.value = student.address
    }

    fun initStudentForAdd() {
        mssv.value = ""
        name.value = ""
        phone.value = ""
        address.value = ""
    }

    fun clearSelectedStudent() {
        _selectedStudent.value = null
    }
}