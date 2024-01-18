package me.amitshekhar.mvvm.data.model

data class Product(
    val id: Int,
    val tenSanPham: String,
    val donGia: Int,
    val donViKho: Int,
    val donViBan: Int,
    val thongTinBaoHanh: String?,
    val thongTinChung: String,
    val manHinh: String?,
    val heDieuHanh: String?,
    val cpu: String?,
    val ram: String?,
    val thietKe: String?,
    val dungLuongPin: String?,
    val danhMuc: Category,
    val hangSanXuat: Manufacturer
)