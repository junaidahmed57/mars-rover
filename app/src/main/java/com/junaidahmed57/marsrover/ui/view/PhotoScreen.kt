package com.junaidahmed57.marsrover.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.junaidahmed57.marsrover.domain.model.RoverPhotoUiState
import com.junaidahmed57.marsrover.ui.photolist.MarsRoverPhotoViewModel

@Composable
fun PhotoScreen(
    roverName: String?,
    sol: String?,
    marsRoverPhotoViewModel: MarsRoverPhotoViewModel
) {
    val viewState by marsRoverPhotoViewModel.roverPhotoUiState.collectAsState()

    if (roverName != null && sol != null){
        LaunchedEffect(Unit) {
            marsRoverPhotoViewModel.getMarsRoverPhotos(roverName,sol)
        }
        when(val roverPhotoUiState = viewState) {
            RoverPhotoUiState.Error -> Error()
            RoverPhotoUiState.Loading -> Loading()
            is RoverPhotoUiState.Success -> PhotoList(roverPhotoUiModelList = roverPhotoUiState.roverPhotoUiModelList) {
                marsRoverPhotoViewModel.changeSaveStatus(it)
            }
        }
    }
}