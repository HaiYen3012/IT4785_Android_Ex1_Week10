package com.example.bai1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SinhVien(
    val mssv: String,
    var hoTen: String,
    var soDienThoai: String = "",
    var diaChi: String = ""
) : Parcelable
