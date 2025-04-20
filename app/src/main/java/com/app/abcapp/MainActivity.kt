package com.app.abcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.app.abcapp.presentation.screens.MainScreen
import com.app.abcapp.presentation.viewmodel.MainViewModel
import com.app.abcapp.presentation.theme.CarouselListAppComposeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarouselListAppComposeTheme {
                MainScreen(viewModel)
            }
        }
    }
}
