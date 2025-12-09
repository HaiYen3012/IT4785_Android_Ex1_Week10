package com.example.bai1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvSinhVien: RecyclerView
    private lateinit var svAdapter: SinhVienAdapter

    private val addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            svAdapter.notifyItemInserted(danhSachSV.size - 1)
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show()
        }
    }

    private val studentDetailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val position = it.data?.getIntExtra("position", -1)
            if (position != null && position != -1) {
                svAdapter.notifyItemChanged(position)
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvSinhVien = findViewById(R.id.rvSinhVien)

        if (danhSachSV.isEmpty()) {
            khoiTaoDuLieuMau()
        }

        svAdapter = SinhVienAdapter(danhSachSV) { position ->
            val intent = Intent(this, StudentDetailActivity::class.java)
            intent.putExtra("student", danhSachSV[position])
            intent.putExtra("position", position)
            studentDetailLauncher.launch(intent)
        }

        rvSinhVien.layoutManager = LinearLayoutManager(this)
        rvSinhVien.adapter = svAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_student -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val danhSachSV = mutableListOf<SinhVien>()

        fun khoiTaoDuLieuMau() {
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
}