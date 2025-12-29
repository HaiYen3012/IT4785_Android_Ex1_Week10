package com.example.bai1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SinhVien(
    val id: String,
    val name: String,
    val phone: String,
    val address: String
) : Parcelable
