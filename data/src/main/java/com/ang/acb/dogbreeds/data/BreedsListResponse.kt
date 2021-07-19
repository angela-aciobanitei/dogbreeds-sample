package com.ang.acb.dogbreeds.data

import com.google.gson.annotations.SerializedName

data class BreedsListResponse(
    @SerializedName("message") val message: Map<String, List<String>>?,
    @SerializedName("status") val status: String,
)


