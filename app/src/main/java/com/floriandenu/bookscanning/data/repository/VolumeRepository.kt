package com.floriandenu.bookscanning.data.repository

import com.floriandenu.bookscanning.api.VolumeApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VolumeRepository @Inject constructor(
    private val volumeApi: VolumeApi
) {

    fun getVolume(isbn: String): Flow<Result<VolumeApi.VolumeResult?>> {
        return flow {
            val response = volumeApi.volumeByIsbn("isbn:$isbn")
            val result = if (response?.isSuccessful == true) {
                Result.success(response.body())
            } else {
                val error = response?.errorBody()?.string()
                Result.failure(Throwable(error))
            }
            emit(result)
        }
    }
}