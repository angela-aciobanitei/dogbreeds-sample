package com.ang.acb.dogbreeds.data.apiresponse

import com.google.gson.annotations.SerializedName

data class BreedImagesResponse(
    @SerializedName("message") val message: List<NetworkImage>?,
    @SerializedName("status") val status: String,
)

