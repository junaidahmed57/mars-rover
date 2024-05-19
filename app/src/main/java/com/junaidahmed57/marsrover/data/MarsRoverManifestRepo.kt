package com.junaidahmed57.marsrover.data

import android.util.Log
import com.junaidahmed57.marsrover.data.service.MarsRoverManifestService
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiState
import com.junaidahmed57.marsrover.domain.model.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarsRoverManifestRepo @Inject constructor(
    private val marsRoverManifestService: MarsRoverManifestService
) {
    fun getMarsRoverManifest(roverName: String): Flow<RoverManifestUiState> = flow {
        try {
            emit(
                toUiModel(
                    marsRoverManifestService.getMarsRoverManifest(
                        roverName.lowercase()
                    )
                )
            )
        } catch (throwable: Throwable){
            throwable.message?.let { Log.d("Error", it) }
            emit(RoverManifestUiState.Error)
        }
    }
}