package com.junaidahmed57.marsrover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.junaidahmed57.marsrover.ui.MarsRoverApp
import com.junaidahmed57.marsrover.BuildConfig
import com.junaidahmed57.marsrover.ui.nav.NavCompose
import com.junaidahmed57.marsrover.ui.theme.MarsRoverTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarsRoverTheme {
                NavCompose()
            }
        }
    }
}
