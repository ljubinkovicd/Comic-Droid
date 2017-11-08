package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class PricesItem(

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("type")
	val type: String? = null
)