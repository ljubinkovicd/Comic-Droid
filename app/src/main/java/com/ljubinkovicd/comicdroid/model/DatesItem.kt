package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class DatesItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)