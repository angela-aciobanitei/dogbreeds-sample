package com.ang.acb.dogbreeds.data

import okio.buffer
import okio.source

object FileReader {
    fun readStringFromFile(fileName: String): String? {
        val inputStream = javaClass.classLoader?.getResourceAsStream("apiresponse/$fileName")
        val source = inputStream?.let {
            inputStream.source().buffer()
        }
        return source?.readString(Charsets.UTF_8)
    }
}
