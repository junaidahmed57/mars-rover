package com.junaidahmed57.marsrover.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.junaidahmed57.marsrover.R
import com.junaidahmed57.marsrover.domain.model.RoverManifestUiModel

@Composable
fun ManifestList(
    roverManifestUiModelList: List<RoverManifestUiModel>,
    roverName: String,
    onClick: (roverName: String, sol: String) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
        ) {
        LazyColumn {
            items(count = roverManifestUiModelList.size) {
                Manifest(roverManifestUiModel = roverManifestUiModelList[it], roverName, onClick)
            }
        }
    }
}

@Composable
fun Manifest(
    roverManifestUiModel: RoverManifestUiModel,
    roverName: String,
    onClick: (roverName: String, sol: String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable { onClick(roverName, roverManifestUiModel.sol) }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.sol, roverManifestUiModel.sol),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(id = R.string.earth_date, roverManifestUiModel.earthDate),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = stringResource(id = R.string.number_of_photos, roverManifestUiModel.photoNumber),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManifestPreview() {
    Manifest(roverManifestUiModel = RoverManifestUiModel(sol = "4", earthDate = "2021-04-21", photoNumber = "25"), roverName = "Perservations", onClick = {_,_->})
}