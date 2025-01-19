package com.example.todoapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun IndeterminateCircularIndicator() {
    var loading by rememberSaveable { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(3000)
        loading = false
    }
    if (loading) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shadowElevation = 4.dp

        ) {
            Box(
                contentAlignment = Alignment.Center, // Aligns children in the center
                modifier = Modifier.fillMaxSize() // Fills the available space
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,

                    )
            }
        }
    } else {
        //   Text("Data Loaded")
    }
}
