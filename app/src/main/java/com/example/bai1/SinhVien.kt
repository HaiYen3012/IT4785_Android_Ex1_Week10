package com.example.bai1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SinhVien(
    val mssv: String,
    var name: String,
    var phone: String = "",
    var address: String = ""
) : Parcelable
