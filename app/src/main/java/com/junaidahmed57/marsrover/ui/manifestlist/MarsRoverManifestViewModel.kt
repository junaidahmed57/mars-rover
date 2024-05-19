package com.junaidahmed57.marsrover.ui.manifestlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junaidahmed57.marsrover.data.MarsRoverManifestRepo
import com.junaidahmed57.marsrover.di.IoDispatcher
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsRoverManifestViewModel @Inject constructor(
    private val marsRoverManifestRepo: MarsRoverManifestRepo,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _roverManifestUiState: MutableStateFlow<RoverManifestUiState> =
        MutableStateFlow(RoverManifestUiState.Loading)

    val roverManifestUiState: StateFlow<RoverManifestUiState>
        get() = _roverManifestUiState

    fun getMarsRoverManifest(roverName: String) {
        viewModelScope.launch(ioDispatcher) {
            marsRoverManifestRepo.getMarsRoverManifest(roverName).collect {
                _roverManifestUiState.value = it
            }
        }
    }

}