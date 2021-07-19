package com.ang.acb.dogbreeds.data

import com.google.gson.annotations.SerializedName

data class BreedImagesResponse(
    @SerializedName("message") val message: List<String>?,
    @SerializedName("status") val status: String,
)

