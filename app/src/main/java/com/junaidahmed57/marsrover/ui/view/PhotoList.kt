package com.junaidahmed57.marsrover.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.junaidahmed57.marsrover.R
import com.junaidahmed57.marsrover.domain.model.RoverPhotoUiModel

@Composable
fun PhotoList(
    roverPhotoUiModelList: List<RoverPhotoUiModel>,
    onClick: (roverPhotoUiModel: RoverPhotoUiModel) -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(count = roverPhotoUiModelList.size){
                Photo(roverPhotoUiModel = roverPhotoUiModelList[it], onClick)
            }
        }
    }
}

@Composable
fun Photo(
    roverPhotoUiModel: RoverPhotoUiModel,
    onClick: (roverPhotoUiModel: RoverPhotoUiModel) -> Unit
) {
    Card(
        modifier = Modifier.padding(16.dp)
            .clickable { onClick(roverPhotoUiModel) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(
                    id = if(roverPhotoUiModel.isSaved) R.drawable.ic_save else R.drawable.ic_save_outline
                ), contentDescription = "save icon")
                Text(
                    text = roverPhotoUiModel.roverName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            AsyncImage(
                model = roverPhotoUiModel.imgSrc,
                contentDescription = "rover photo",
                modifier = Modifier.height(300.dp)
            )
            Text(
                text = stringResource(id = R.string.sol, roverPhotoUiModel.sol),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(id = R.string.earth_date, roverPhotoUiModel.earthDate),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = roverPhotoUiModel.cameraFullName,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PhotoPreview() {
    Photo(roverPhotoUiModel = RoverPhotoUiModel(
        id = 4,
        roverName = "Curiosity",
        sol = "45",
        earthDate = "2021-05-15",
        imgSrc = "",
        cameraFullName = "Camera Right",
        isSaved = true
    ), {})
}