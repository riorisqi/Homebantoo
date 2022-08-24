package com.example.homebantoo.api

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("results")
	val results: List<ResultsItem>,

	@field:SerializedName("status")
	val status: Boolean
)

data class ResultsItem(

	@field:SerializedName("difficulty")
	val difficulty: String,

	@field:SerializedName("times")
	val times: String,

	@field:SerializedName("thumb")
	val thumb: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("key")
	val key: String,

	@field:SerializedName("serving")
	val serving: String
)
