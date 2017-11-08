package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ljubinkovicd on 11/7/17.
 */
data class ComicData(

        @field:SerializedName("total")
        val total: Int? = null,

        @field:SerializedName("offset")
        val offset: Int? = null,

        @field:SerializedName("limit")
        val limit: Int? = null,

        @field:SerializedName("count")
        val count: Int? = null,

        @field:SerializedName("results")
        val results: List<Comic?>? = null

//	@field:SerializedName("results")
//	val results: List<*>
)