package com.floriandenu.bookscanning.barcodescanner.domain

import com.floriandenu.bookscanning.api.VolumeApi
import com.floriandenu.bookscanning.data.repository.VolumeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVolumeUseCase @Inject constructor(private val volumeRepository: VolumeRepository) {

    operator fun invoke(isbn: String): Flow<Result<VolumeApi.VolumeResult?>> {
        return volumeRepository.getVolume(isbn)
    }
}