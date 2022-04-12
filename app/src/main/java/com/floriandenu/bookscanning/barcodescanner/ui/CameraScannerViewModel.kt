package com.floriandenu.bookscanning.barcodescanner.ui

import androidx.lifecycle.ViewModel
import com.floriandenu.bookscanning.barcodescanner.domain.GetVolumeUseCase
import javax.inject.Inject

class CameraScannerViewModel @Inject constructor(
    private val getVolumeUseCase: GetVolumeUseCase
): ViewModel() {
}