package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class Series(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("resourceURI")
	val resourceURI: String? = null
)