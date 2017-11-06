package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class Thumbnail(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("extension")
	val extension: String? = null
)