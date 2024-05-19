package com.junaidahmed57.marsrover.domain.model

import com.junaidahmed57.marsrover.data.service.model.RoverManifestRemoteModel

fun toUiModel(roverManifestRemoteModel: RoverManifestRemoteModel): RoverManifestUiState =
    RoverManifestUiState.Success(roverManifestRemoteModel.photoManifest.photos.map { photo ->
        RoverManifestUiModel(
            sol = photo.sol.toString(),
            earthDate = photo.earthDate,
            photoNumber = photo.totalPhotos.toString()
        )
    }.sorted())