@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.junaidahmed57.marsrover.ui.view

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junaidahmed57.marsrover.R
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiState
import com.junaidahmed57.marsrover.ui.manifestlist.MarsRoverManifestViewModel

@Composable
fun SharedTransitionScope.ManifestScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    roverName: String?,
    roverImg: Int?,
    marsRoverManifestViewModel: MarsRoverManifestViewModel,
    onClick: (roverName: String, sol: String) -> Unit
) {
    val viewState by marsRoverManifestViewModel.roverManifestUiState.collectAsState()

    Column {
        Image(
            painter = painterResource(id = roverImg?:R.drawable.perseverance),
            contentDescription = roverName,
            modifier = Modifier.fillMaxWidth()
                .sharedElement(
                    state = rememberSharedContentState(key = "image/$roverName"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _,_ ->
                        tween(1000)
                    }
                )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = roverName?:"",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                .sharedElement(
                    state = rememberSharedContentState(key = "text/$roverName"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    boundsTransform = { _,_ ->
                        tween(1000)
                    }
                )
        )

        if (roverName != null) {
            LaunchedEffect(Unit) {
                marsRoverManifestViewModel.getMarsRoverManifest(roverName)
            }
            when(val roverManifestUiState = viewState) {
                RoverManifestUiState.Error -> Error()
                RoverManifestUiState.Loading -> Loading()
                is RoverManifestUiState.Success -> ManifestList(
                    roverManifestUiModelList = roverManifestUiState.roverManifestUiModelList,
                    roverName = roverName,
                    onClick = onClick
                )
            }
        }
    }
}

@Preview
@Composable
fun ManifestScreenPreview() {
//    ManifestScreen("Perseverance")
}