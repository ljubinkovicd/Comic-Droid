package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("resourceURI")
	val resourceURI: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)