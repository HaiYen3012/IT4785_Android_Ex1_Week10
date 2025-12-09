package com.example.bai1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var tvMssv: TextView
    private lateinit var etHoTen: TextInputEditText
    private lateinit var etSoDienThoai: TextInputEditText
    private lateinit var etDiaChi: TextInputEditText
    private lateinit var btnUpdate: MaterialButton
    private lateinit var btnCancel: MaterialButton

    private var position: Int = -1
    private lateinit var sinhVien: SinhVien

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_detail)

        supportActionBar?.title = "Chi tiết sinh viên"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvMssv = findViewById(R.id.tvMssv)
        etHoTen = findViewById(R.id.etHoTen)
        etSoDienThoai = findViewById(R.id.etSoDienThoai)
        etDiaChi = findViewById(R.id.etDiaChi)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnCancel = findViewById(R.id.btnCancel)

        sinhVien = intent.getParcelableExtra<SinhVien>("student") ?: run {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin sinh viên", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        position = intent.getIntExtra("position", -1)

        hienThiThongTin()

        btnUpdate.setOnClickListener {
            capNhatSinhVien()
        }

        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun hienThiThongTin() {
        tvMssv.text = sinhVien.mssv
        etHoTen.setText(sinhVien.hoTen)
        etSoDienThoai.setText(sinhVien.soDienThoai)
        etDiaChi.setText(sinhVien.diaChi)
    }

    private fun capNhatSinhVien() {
        val hoTen = etHoTen.text.toString().trim()
        val soDienThoai = etSoDienThoai.text.toString().trim()
        val diaChi = etDiaChi.text.toString().trim()

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

        if (position != -1) {
            MainActivity.danhSachSV[position].hoTen = hoTen
            MainActivity.danhSachSV[position].soDienThoai = soDienThoai
            MainActivity.danhSachSV[position].diaChi = diaChi

            val resultIntent = Intent()
            resultIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}