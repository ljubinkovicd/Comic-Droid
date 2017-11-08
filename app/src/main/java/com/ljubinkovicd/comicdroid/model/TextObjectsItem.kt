package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class TextObjectsItem(

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)