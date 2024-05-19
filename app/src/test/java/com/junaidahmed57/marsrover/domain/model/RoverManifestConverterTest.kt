package com.junaidahmed57.marsrover.domain.model

import com.junaidahmed57.marsrover.data.service.model.ManifestPhotoRemoteModel
import com.junaidahmed57.marsrover.data.service.model.PhotoManifestRemoteModel
import com.junaidahmed57.marsrover.data.service.model.RoverManifestRemoteModel
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiModel
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiState
import com.junaidahmed57.marsrover.domain.model.toUiModel
import org.junit.Test

import org.junit.Assert.*


class RoverManifestConverterTest {
    @Test
    fun convert_roverManifestRemoteModel_toRoverManifestUiSate() {
        //Given
        val roverManifestRemoteModel = RoverManifestRemoteModel(
            photoManifest = PhotoManifestRemoteModel(
                landingDate = "2021-05-09",
                name = "Curiosity",
                maxSol = 1105,
                maxDate = "2023-08-15",
                launchDate = "2021-08-15",
                photos = listOf(
                    ManifestPhotoRemoteModel(
                        cameras = listOf("REAR_CAM", "LEFT_CAM"),
                        earthDate = "2022-09-25",
                        totalPhotos = 42,
                        sol = 1
                    ),
                    ManifestPhotoRemoteModel(
                        cameras = listOf("LEFT_CAM", "EDL_PDDCAM"),
                        earthDate = "2021-02-19",
                        sol = 1107,
                        totalPhotos = 201
                    )
                ),
                status = "active",
                totalPhotos = 1250
            ),
        )

        //When
        val result = toUiModel(roverManifestRemoteModel)

        //Then
        val expectedResult = RoverManifestUiState.Success(
            listOf(
                RoverManifestUiModel(
                    sol = "1",
                    earthDate = "2022-09-25",
                    photoNumber = "42"
                ),
                RoverManifestUiModel(
                    sol = "1107",
                    earthDate = "2021-02-19",
                    photoNumber = "201"
                )
            )
        )
        assertEquals(expectedResult, result)

    }
}