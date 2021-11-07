package com.example.appadoskotlin2.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ServiceItemResponse (
    val userId: String?,
    val idService: String?,
    val title: String?,
    val offer: Boolean
    ):Parcelable
