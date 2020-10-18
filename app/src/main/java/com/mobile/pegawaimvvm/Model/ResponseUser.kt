package com.mobile.pegawai.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseUser(

	@field:SerializedName("result")
	val result: List<ResultItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class ResultItem(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("id_user")
	val id_user: String? = null,

	@field:SerializedName("email")
	val email: String? = null
):Parcelable
