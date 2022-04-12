package com.floriandenu.bookscanning.booklist.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.floriandenu.bookscanning.Screen

@Composable
fun BookList(
    modifier: Modifier = Modifier,
    navigateTo: ((Screen) -> Unit)
) {
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Text(text = "You will see content here")
                Text(text = "You will see content here")
                Text(text = "You will see content here")
                Text(text = "You will see content here")
            }
        }
        AddBookWithCameraPermissionFloatingButton(navigateTo = navigateTo)
    }
}

@Composable
fun AddBookWithCameraPermissionFloatingButton(
    navigateTo: ((Screen) -> Unit)
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isCameraPermissionGranted ->
            if (isCameraPermissionGranted) navigateTo(Screen.AddBookByBarCodeScanning)
        })
    val context = LocalContext.current
    FloatingActionButton(
        onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) -> {
                    navigateTo(Screen.AddBookByBarCodeScanning)
                }
                else -> {
                    launcher.launch(Manifest.permission.CAMERA)
                }
            }
        },
        modifier = Modifier.padding(end = 32.dp, bottom = 32.dp)
    ) {
        Icon(Icons.Filled.Add, "")
    }
}

