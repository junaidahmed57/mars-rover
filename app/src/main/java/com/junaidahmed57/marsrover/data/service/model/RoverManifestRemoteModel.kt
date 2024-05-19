package com.junaidahmed57.marsrover.data.service.model

import com.google.gson.annotations.SerializedName

data class RoverManifestRemoteModel(
    @field:SerializedName("photo_manifest")
    val photoManifest: PhotoManifestRemoteModel
)
