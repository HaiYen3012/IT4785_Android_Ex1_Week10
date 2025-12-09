package com.example.bai1

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class AddStudentActivity : AppCompatActivity() {

    private lateinit var etMssv: TextInputEditText
    private lateinit var etHoTen: TextInputEditText
    private lateinit var etSoDienThoai: TextInputEditText
    private lateinit var etDiaChi: TextInputEditText
    private lateinit var btnAdd: MaterialButton
    private lateinit var btnCancel: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        supportActionBar?.title = "Thêm sinh viên"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        etMssv = findViewById(R.id.etMssv)
        etHoTen = findViewById(R.id.etHoTen)
        etSoDienThoai = findViewById(R.id.etSoDienThoai)
        etDiaChi = findViewById(R.id.etDiaChi)
        btnAdd = findViewById(R.id.btnAdd)
        btnCancel = findViewById(R.id.btnCancel)

        btnAdd.setOnClickListener {
            themSinhVien()
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun themSinhVien() {
        val mssv = etMssv.text.toString().trim()
        val hoTen = etHoTen.text.toString().trim()
        val soDienThoai = etSoDienThoai.text.toString().trim()
        val diaChi = etDiaChi.text.toString().trim()

        if (mssv.isEmpty()) {
            etMssv.error = "Mã số sinh viên không được để trống"
            etMssv.requestFocus()
            return
        }

        if (MainActivity.danhSachSV.any { it.mssv.equals(mssv, ignoreCase = true) }) {
            etMssv.error = "Mã số sinh viên đã tồn tại"
            etMssv.requestFocus()
            return
        }

        if (hoTen.isEmpty()) {
            etHoTen.error = "Họ tên không được để trống"
            etHoTen.requestFocus()
            return
        }

        if (soDienThoai.isEmpty()) {
            etSoDienThoai.error = "Số điện thoại không được để trống"
            etSoDienThoai.requestFocus()
            return
        }

        if (diaChi.isEmpty()) {
            etDiaChi.error = "Địa chỉ không được để trống"
            etDiaChi.requestFocus()
            return
        }

        val newStudent = SinhVien(mssv, hoTen, soDienThoai, diaChi)
        MainActivity.danhSachSV.add(newStudent)

        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}