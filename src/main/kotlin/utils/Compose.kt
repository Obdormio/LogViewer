package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun <T> useState(initialValue: T) = remember { mutableStateOf(initialValue) }
