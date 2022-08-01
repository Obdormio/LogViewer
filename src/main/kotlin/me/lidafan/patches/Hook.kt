package me.lidafan.patches

import androidx.compose.runtime.CheckResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class Ref<T>(var value: T)

@Suppress("unused")
@Composable
fun <T> rememberRef(value: T) = remember { Ref(value) }
