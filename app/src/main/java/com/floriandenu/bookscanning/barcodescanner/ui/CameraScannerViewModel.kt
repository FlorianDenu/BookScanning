package com.floriandenu.bookscanning.barcodescanner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.floriandenu.bookscanning.barcodescanner.domain.GetVolumeUseCase
import com.floriandenu.bookscanning.utils.BarCodeAnalyzerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CameraScannerViewModel @Inject constructor(
    private val getVolumeUseCase: GetVolumeUseCase
) : ViewModel() {

    val barCodeResult = MutableStateFlow<BarCodeAnalyzerResult>(BarCodeAnalyzerResult.Analyzing)

    val uiUpdate= MutableStateFlow<UIState>(UIState.Analyzing)

    init {
        viewModelScope.launch {
            barCodeResult.filterNotNull().distinctUntilChanged().collect {
                when (it) {
                    is BarCodeAnalyzerResult.OnSuccess -> {
                        uiUpdate.emit(UIState.OnLoading(it.isbn))
                        getVolume(it.isbn)
                    }
                    is BarCodeAnalyzerResult.Analyzing -> uiUpdate.emit(UIState.Analyzing)
                    is BarCodeAnalyzerResult.OnFailure -> uiUpdate.emit(UIState.OnFailure(it.message))
                }
            }
        }
    }

    private fun getVolume(isbn: String) {
        viewModelScope.launch {
            try {
                getVolumeUseCase(isbn).firstOrNull()?.getOrNull()?.items?.firstOrNull()?.id?.let { id ->
                    uiUpdate.emit(UIState.OnSuccess(id))
                }
            } catch (e: Exception) {
                e.message?.let { uiUpdate.emit(UIState.OnFailure(it)) }
            }
        }
    }
}

sealed class UIState(val message: String) {
    object Analyzing: UIState("Analyzing")
    data class OnSuccess(val id: String): UIState("Find book with id $id")
    data class OnFailure(val errorMessage: String): UIState(errorMessage)
    data class OnLoading(val isbn: String): UIState("We detect isbn: $isbn we are loading data")
}