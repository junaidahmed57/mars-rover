package com.junaidahmed57.marsrover.domain.model

import com.junaidahmed57.marsrover.data.db.MarsRoverSavedLocalModel

fun toDbModel(photoUiModel: RoverPhotoUiModel): MarsRoverSavedLocalModel =
    MarsRoverSavedLocalModel(
        roverPhotoId = photoUiModel.id,
        roverName = photoUiModel.roverName,
        imgSrc = photoUiModel.imgSrc,
        sol = photoUiModel.sol,
        earthDate = photoUiModel.earthDate,
        cameraFullName = photoUiModel.cameraFullName
    )

fun toUiModel(marsRoverSavedLocalModelList: List<MarsRoverSavedLocalModel>) =
    marsRoverSavedLocalModelList.map { photo ->
        RoverPhotoUiModel(
            id = photo.roverPhotoId,
            roverName = photo.roverName,
            sol = photo.sol,
            imgSrc = photo.imgSrc,
            earthDate = photo.earthDate,
            cameraFullName = photo.cameraFullName,
            isSaved = true
        )
    }