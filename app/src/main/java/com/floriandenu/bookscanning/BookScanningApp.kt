package com.floriandenu.bookscanning

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.floriandenu.bookscanning.barcodescanner.ui.CameraPreview
import com.floriandenu.bookscanning.booklist.ui.BookList
import com.floriandenu.bookscanning.ui.theme.BookScanningTheme

@Composable
fun BookScanningApp(
    navigationViewModel: NavigationViewModel = viewModel()
) {
    BookScanningTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            AppContent(navigationViewModel = navigationViewModel)
        }
    }

}

@Composable
fun AppContent(
    navigationViewModel: NavigationViewModel
) {
    when(navigationViewModel.currentScreen) {
        Screen.Home -> BookList(navigateTo= navigationViewModel::navigateTo)
        Screen.AddBookByBarCodeScanning -> CameraPreview()
        is Screen.BookDetails -> { /* TO-DO */}
    }
}