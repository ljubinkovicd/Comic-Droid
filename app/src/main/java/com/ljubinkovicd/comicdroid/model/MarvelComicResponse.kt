package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ljubinkovicd on 11/7/17.
 */
data class MarvelComicResponse (
    @field:SerializedName("copyright")
    val copyright: String? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: ComicData? = null,

    @field:SerializedName("attributionHTML")
    val attributionHTML: String? = null,

    @field:SerializedName("attributionText")
    val attributionText: String? = null,

    @field:SerializedName("etag")
    val etag: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)