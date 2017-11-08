package com.ljubinkovicd.comicdroid.model

import com.google.gson.annotations.SerializedName

data class Comic(

	@field:SerializedName("creators")
	val creators: Creators? = null,

	@field:SerializedName("issueNumber")
	val issueNumber: Int? = null,

	@field:SerializedName("isbn")
	val isbn: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("variants")
	val variants: List<Any?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("diamondCode")
	val diamondCode: String? = null,

	@field:SerializedName("characters")
	val characters: Characters? = null,

	@field:SerializedName("urls")
	val urls: List<UrlsItem?>? = null,

	@field:SerializedName("ean")
	val ean: String? = null,

	@field:SerializedName("collections")
	val collections: List<Any?>? = null,

	@field:SerializedName("modified")
	val modified: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("prices")
	val prices: List<PricesItem?>? = null,

	@field:SerializedName("events")
	val events: Events? = null,

	@field:SerializedName("collectedIssues")
	val collectedIssues: List<Any?>? = null,

	@field:SerializedName("pageCount")
	val pageCount: Int? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Thumbnail? = null,

	@field:SerializedName("images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("stories")
	val stories: Stories? = null,

	@field:SerializedName("textObjects")
	val textObjects: List<TextObjectsItem?>? = null,

	@field:SerializedName("digitalId")
	val digitalId: Int? = null,

	@field:SerializedName("format")
	val format: String? = null,

	@field:SerializedName("upc")
	val upc: String? = null,

	@field:SerializedName("dates")
	val dates: List<DatesItem?>? = null,

	@field:SerializedName("resourceURI")
	val resourceURI: String? = null,

	@field:SerializedName("variantDescription")
	val variantDescription: String? = null,

	@field:SerializedName("issn")
	val issn: String? = null,

	@field:SerializedName("series")
	val series: Series? = null
)