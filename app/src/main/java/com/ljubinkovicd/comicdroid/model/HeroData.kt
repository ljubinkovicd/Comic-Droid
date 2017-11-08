package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class HeroData(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("offset")
	val offset: Int? = null,

	@field:SerializedName("limit")
	val limit: Int? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<Hero?>? = null

//	@field:SerializedName("results")
//	val results: List<*>
)