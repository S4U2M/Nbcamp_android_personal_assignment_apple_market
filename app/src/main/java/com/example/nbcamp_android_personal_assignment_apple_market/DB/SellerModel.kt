package com.example.nbcamp_android_personal_assignment_apple_market.DB

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerModel(
    val id: Int, val img: Int, val title: String, val detail: String,
    val name: String, val price: Int, val homeAdress:String,
    var like: Int, val chat: Int
) : Parcelable
