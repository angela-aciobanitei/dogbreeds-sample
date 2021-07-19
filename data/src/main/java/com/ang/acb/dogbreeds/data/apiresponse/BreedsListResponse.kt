package com.ang.acb.dogbreeds.data.apiresponse

import com.google.gson.annotations.SerializedName

data class BreedsListResponse(
    @SerializedName("message") val message: NetworkBreeds?,
    @SerializedName("status") val status: String,
)


