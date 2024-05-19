package com.junaidahmed57.marsrover.data

import com.junaidahmed57.marsrover.data.db.MarsRoverSavedPhotoDao
import com.junaidahmed57.marsrover.data.service.MarsRoverPhotoService
import com.junaidahmed57.marsrover.data.service.model.RoverPhotoRemoteModel
import com.junaidahmed57.marsrover.domain.model.RoverPhotoUiModel
import com.junaidahmed57.marsrover.domain.model.RoverPhotoUiState
import com.junaidahmed57.marsrover.domain.model.toDbModel
import com.junaidahmed57.marsrover.domain.model.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MarsRoverPhotoRepo @Inject constructor(
    private val marsRoverPhotoService: MarsRoverPhotoService,
    private val marsRoverSavedPhotoDao: MarsRoverSavedPhotoDao
) {

    private fun getAllRemoteMarsRoverPhotos(
        roverName: String,
        sol: String
    ): Flow<RoverPhotoRemoteModel?> = flow {
        try {
            val networkResult = marsRoverPhotoService.getMarsRoverPhotos(
                roverName = roverName.lowercase(),
                sol = sol
            )
            emit(networkResult)
        } catch (throwable: Throwable){
            emit(null)
        }
    }

    fun getMarRoverPhoto(roverName: String, sol: String): Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.allSavedIds(sol,roverName).combine(
            getAllRemoteMarsRoverPhotos(roverName,sol)
        ) { local, remote ->
            remote ?.let {roverPhotoRemoteModel ->
                RoverPhotoUiState.Success(
                    roverPhotoRemoteModel.photos.map {photoRemoteModel ->
                        RoverPhotoUiModel(
                            id = photoRemoteModel.id,
                            roverName = photoRemoteModel.rover.name,
                            imgSrc = photoRemoteModel.imgSrc,
                            sol = photoRemoteModel.sol.toString(),
                            earthDate = photoRemoteModel.earthDate,
                            cameraFullName = photoRemoteModel.camera.fullName,
                            isSaved = local.contains(photoRemoteModel.id)
                        )
                    }
                )
            } ?: run { RoverPhotoUiState.Error }
        }

    suspend fun savePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.insert(toDbModel(roverPhotoUiModel))
    }

    suspend fun removePhoto(roverPhotoUiModel: RoverPhotoUiModel) {
        marsRoverSavedPhotoDao.delete(toDbModel(roverPhotoUiModel))
    }

    fun getAllSaved(): Flow<RoverPhotoUiState> =
        marsRoverSavedPhotoDao.getAllSaved().map { localModel ->
            RoverPhotoUiState.Success(toUiModel(localModel ))
        }

//    fun getMarRoverPhoto(roverName: String, sol: String): Flow<RoverPhotoUiState> = flow {
//        try {
//            val networkResult = marsRoverPhotoService.getMarsRoverPhotos(roverName, sol)
//            emit(RoverPhotoUiState.Success(
//                networkResult.photos.map { photo ->
//                    RoverPhotoUiModel(
//                        id = photo.id,
//                        roverName = photo.rover.name,
//                        imgSrc = photo.imgSrc,
//                        sol = photo.sol.toString(),
//                        earthDate = photo.earthDate,
//                        cameraFullName = photo.camera.fullName
//                    )
//                }
//            ))
//        } catch (throwable: Throwable) {
//            emit(RoverPhotoUiState.Error)
//        }
//    }
}