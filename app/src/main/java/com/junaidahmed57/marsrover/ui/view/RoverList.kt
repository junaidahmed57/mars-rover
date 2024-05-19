@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.junaidahmed57.marsrover.ui.view

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junaidahmed57.marsrover.R
import com.junaidahmed57.marsrover.domain.model.roverUiModelList

@Composable
fun SharedTransitionScope.RoverList(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClickAction: (roverName: String, roverImg: Int) -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(count = roverUiModelList.size) {
                val rover = roverUiModelList[it]
                Rover(
                    animatedVisibilityScope = animatedVisibilityScope,
                    name = rover.name,
                    img = rover.img,
                    landingDate = rover.landingDate,
                    distanceTraveled = rover.distance,
                    onClickAction)
            }
        }
    }
}

@Composable
fun SharedTransitionScope.Rover(
    animatedVisibilityScope: AnimatedVisibilityScope,
    name: String,
    @DrawableRes
    img: Int,
    landingDate: String,
    distanceTraveled: String,
    onClickAction: (roverName: String, roverImg: Int) -> Unit
){
    Card(
        Modifier
            .padding(16.dp)
            .clickable { onClickAction(name,img) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .sharedElement(
                        state = rememberSharedContentState(key = "text/$name"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _,_ ->
                            tween(1000)
                        }
                    )
            )
            Image(
                painter = painterResource(id = img),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .sharedElement(
                        state = rememberSharedContentState(key = "image/$name"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _,_ ->
                            tween(1000)
                        }
                    )
            )
            Text(text = "Credit: NASA/JPL", style = MaterialTheme.typography.labelSmall)
            Text(text = "Landing Date: $landingDate", style = MaterialTheme.typography.bodySmall)
            Text(text = "Distance traveled: $distanceTraveled", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
fun RoverPreview(){
//    Rover("Perseverance", R.drawable.perseverance, "18 February 2021", "12.56 km", {})
}

@Preview
@Composable
fun RoverListPreview(){
//    RoverList(null,{})
}