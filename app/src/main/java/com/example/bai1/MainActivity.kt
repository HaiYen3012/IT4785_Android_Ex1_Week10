package com.example.bai1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etMssv: EditText
    private lateinit var etHoTen: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var rvSinhVien: RecyclerView

    private val danhSachSV = mutableListOf<SinhVien>()
    private lateinit var svAdapter: SinhVienAdapter
    private var selectedPosition: Int = -1 // Biến để lưu vị trí sinh viên đang được chọn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ các view từ layout
        etMssv = findViewById(R.id.etMssv)
        etHoTen = findViewById(R.id.etHoTen)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        rvSinhVien = findViewById(R.id.rvSinhVien)

        // Thêm một vài dữ liệu mẫu
        khoiTaoDuLieuMau()

        // Khởi tạo Adapter
        svAdapter = SinhVienAdapter(
            sinhVienList = danhSachSV,
            onItemClick = { position ->
                // Khi người dùng nhấn vào một sinh viên trong danh sách
                val sv = danhSachSV[position]
                etMssv.setText(sv.mssv)
                etHoTen.setText(sv.hoTen)
                etMssv.isEnabled = false // Không cho sửa MSSV
                selectedPosition = position
            },
            onDeleteClick = { position ->
                // Khi người dùng nhấn nút xóa
                xoaSinhVien(position)
            }
        )

        // Thiết lập RecyclerView
        rvSinhVien.layoutManager = LinearLayoutManager(this)
        rvSinhVien.adapter = svAdapter

        // Xử lý sự kiện cho nút Add
        btnAdd.setOnClickListener {
            themSinhVien()
        }

        // Xử lý sự kiện cho nút Update
        btnUpdate.setOnClickListener {
            capNhatSinhVien()
        }
    }

    private fun themSinhVien() {
        val mssv = etMssv.text.toString().trim()
        val hoTen = etHoTen.text.toString().trim()

        if (mssv.isEmpty() || hoTen.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            return
        }

        // Kiểm tra MSSV đã tồn tại chưa
        if (danhSachSV.any { it.mssv == mssv }) {
            Toast.makeText(this, "Mã số sinh viên đã tồn tại", Toast.LENGTH_SHORT).show()
            return
        }

        val sv = SinhVien(mssv, hoTen)
        danhSachSV.add(sv)
        svAdapter.notifyItemInserted(danhSachSV.size - 1) // Thông báo cho Adapter có item mới
        xoaTrangInput()
        Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
    }

    private fun capNhatSinhVien() {
        val hoTenMoi = etHoTen.text.toString().trim()

        if (selectedPosition == -1) {
            Toast.makeText(this, "Vui lòng chọn một sinh viên để cập nhật", Toast.LENGTH_SHORT).show()
            return
        }

        if (hoTenMoi.isEmpty()) {
            Toast.makeText(this, "Họ tên không được để trống", Toast.LENGTH_SHORT).show()
            return
        }

        danhSachSV[selectedPosition].hoTen = hoTenMoi
        svAdapter.notifyItemChanged(selectedPosition) // Thông báo cho Adapter item đã thay đổi
        xoaTrangInput()
        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
    }

    private fun xoaSinhVien(position: Int) {
        danhSachSV.removeAt(position)
        svAdapter.notifyItemRemoved(position)
        // Nếu sinh viên đang được chọn bị xóa, hãy xóa trắng input
        if (position == selectedPosition) {
            xoaTrangInput()
        }
        Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show()
    }

    private fun xoaTrangInput() {
        etMssv.text.clear()
        etHoTen.text.clear()
        etMssv.isEnabled = true // Cho phép nhập lại MSSV
        selectedPosition = -1 // Reset vị trí đang chọn
        etMssv.requestFocus()
    }

    private fun khoiTaoDuLieuMau() {
        danhSachSV.add(SinhVien("20200001", "Nguyễn Văn A"))
        danhSachSV.add(SinhVien("20200002", "Trần Thị B"))
        danhSachSV.add(SinhVien("20200003", "Lê Văn C"))
        danhSachSV.add(SinhVien("20200004", "Phạm Thị D"))
        danhSachSV.add(SinhVien("20200005", "Hoàng Văn E"))
        danhSachSV.add(SinhVien("20200006", "Vũ Thị F"))
        danhSachSV.add(SinhVien("20200007", "Đặng Văn G"))
        danhSachSV.add(SinhVien("20200008", "Bùi Thị H"))
    }
}
