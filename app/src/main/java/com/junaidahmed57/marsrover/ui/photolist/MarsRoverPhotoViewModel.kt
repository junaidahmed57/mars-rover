package com.junaidahmed57.marsrover.ui.photolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junaidahmed57.marsrover.data.MarsRoverPhotoRepo
import com.junaidahmed57.marsrover.domain.model.RoverPhotoUiModel
import com.junaidahmed57.marsrover.domain.model.RoverPhotoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsRoverPhotoViewModel @Inject constructor(
    private val marsRoverPhotoRepo: MarsRoverPhotoRepo
): ViewModel() {

    private val _roverPhotoUiState: MutableStateFlow<RoverPhotoUiState> =
        MutableStateFlow(RoverPhotoUiState.Loading)

    val roverPhotoUiState: MutableStateFlow<RoverPhotoUiState>
        get() = _roverPhotoUiState

    fun getMarsRoverPhotos(roverName: String, sol: String) {
        viewModelScope.launch {
            _roverPhotoUiState.value = RoverPhotoUiState.Loading
            marsRoverPhotoRepo.getMarRoverPhoto(roverName, sol).collect{
                _roverPhotoUiState.value = it
            }
        }
    }

    fun changeSaveStatus(roverPhotoUiModel: RoverPhotoUiModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if(roverPhotoUiModel.isSaved){
                marsRoverPhotoRepo.removePhoto(roverPhotoUiModel)
            }
            else {
                marsRoverPhotoRepo.savePhoto(roverPhotoUiModel)
            }
        }
    }

}