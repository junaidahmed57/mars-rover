package com.junaidahmed57.marsrover.ui.manifestlist

import com.junaidahmed57.marsrover.MainCoroutineRule
import com.junaidahmed57.marsrover.data.MarsRoverManifestRepo
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiModel
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiState
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class MarsRoverViewModelTest {

    private val marsRoverManifestRepo = mockkClass(MarsRoverManifestRepo::class)

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun marsRoverManifestRepo_returnError_shouldEmitError() = runTest(coroutineRule.testDispatcher) {
        //Given
        coEvery {
            marsRoverManifestRepo.getMarsRoverManifest("curiosity")
        } returns flowOf(RoverManifestUiState.Error)

        //When
        val marsRoverManifestViewModel = MarsRoverManifestViewModel(
            marsRoverManifestRepo,
            coroutineRule.testDispatcher
        )
        marsRoverManifestViewModel.getMarsRoverManifest("curiosity")
        val result = marsRoverManifestViewModel.roverManifestUiState.first()

        Assert.assertEquals(RoverManifestUiState.Error, result)

    }

    @Test
    fun marsRoverManifestRepo_returnSuccess_shouldEmitSuccess() = runTest(coroutineRule.testDispatcher) {
        //Given
        val expectedValue = RoverManifestUiState.Success(
            listOf(
                RoverManifestUiModel(
                    sol = "1",
                    earthDate = "2021-05-22",
                    photoNumber = "21"
                ),
                RoverManifestUiModel(
                    sol = "0",
                    earthDate = "2021-02-15",
                    photoNumber = "54"
                )
            )
        )
        coEvery {
            marsRoverManifestRepo.getMarsRoverManifest("curiosity")
        } returns flowOf(expectedValue)

        //When
        val marsRoverManifestViewModel = MarsRoverManifestViewModel(
            marsRoverManifestRepo,
            coroutineRule.testDispatcher
        )
        marsRoverManifestViewModel.getMarsRoverManifest("curiosity")
        val result = marsRoverManifestViewModel.roverManifestUiState.first()

        Assert.assertEquals(expectedValue, result)

    }

}