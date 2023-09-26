package com.example.filmoteka.model.data

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException


data class ErrorDTO(
    val statusCode: Int,
    val message: String,
    val error: String,
) {
    companion object {
        fun parseError(retrofit: Retrofit, response: Response<*>): ErrorDTO? {
            val converter: Converter<ResponseBody, ErrorDTO> =
                retrofit.responseBodyConverter(ErrorDTO::class.java, arrayOfNulls<Annotation>(0))

            return try {
                converter.convert(response.errorBody()!!)
            } catch (e: IOException) {
                ErrorDTO(
                    statusCode = 0,
                    message = e.localizedMessage ?: "Internal error",
                    error = "internal"
                )
            }
        }
    }
}