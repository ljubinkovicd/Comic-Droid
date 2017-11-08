package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class ImagesItem(

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("extension")
	val extension: String? = null
)