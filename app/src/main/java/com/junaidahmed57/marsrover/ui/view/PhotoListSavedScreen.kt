package com.junaidahmed57.marsrover.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.junaidahmed57.marsrover.domain.model.RoverPhotoUiState
import com.junaidahmed57.marsrover.ui.savedlist.MarsRoverSaveViewModel

@Composable
fun PhotoListSavedScreen(
    modifier: Modifier = Modifier,
    marsRoverSaveViewModel: MarsRoverSaveViewModel
) {
    val viewState by marsRoverSaveViewModel.marsPhotoUiSavedState.collectAsState()

    LaunchedEffect(Unit) {
        marsRoverSaveViewModel.getAllSaved()
    }
    when( val roverPhotoUiState = viewState ) {
        RoverPhotoUiState.Error -> Error()
        RoverPhotoUiState.Loading -> Loading()
        is RoverPhotoUiState.Success -> PhotoList(
            roverPhotoUiModelList = roverPhotoUiState.roverPhotoUiModelList,
            onClick = {
                marsRoverSaveViewModel.changeSaveStatus(it)
            }
        )
    }
}