package com.floriandenu.bookscanning.utils

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.isSystemInDarkTheme
import com.floriandenu.bookscanning.BuildConfig
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

class BarcodeAnalyzer(override val coroutineContext: CoroutineContext,
                      private val result: MutableStateFlow<BarCodeAnalyzerResult>
): CoroutineScope, ImageAnalysis.Analyzer {

    private val scanner = BarcodeScanning.getClient()

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        CoroutineScope(coroutineContext).launch {
                            barcode.displayValue?.let {
                                result.emit(BarCodeAnalyzerResult.OnSuccess(it))
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    CoroutineScope(coroutineContext).launch {
                        it.message?.let {
                            result.emit(BarCodeAnalyzerResult.OnFailure(it))
                        }
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}

sealed class BarCodeAnalyzerResult {
    data class OnSuccess(val isbn: String): BarCodeAnalyzerResult()
    data class OnFailure(val message: String): BarCodeAnalyzerResult()
    object Analyzing: BarCodeAnalyzerResult()
}