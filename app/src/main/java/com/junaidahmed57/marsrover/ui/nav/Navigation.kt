package com.junaidahmed57.marsrover.ui.nav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.junaidahmed57.marsrover.R
import com.junaidahmed57.marsrover.ui.nav.Destinations.HOME
import com.junaidahmed57.marsrover.ui.nav.Destinations.SAVED
import kotlinx.serialization.Serializable

object Destinations {
    const val HOME = "Home"
    const val SAVED = "Saved"
    const val MANIFEST = "Manifest"
    const val PHOTO = "Photo"
}

@Serializable
object Home

@Serializable
data class Manifest(
    val roverName: String?,
    val roverImg: Int,
)

@Serializable
data class Photo(
    val roverName: String?,
    val sol: String?
)

@Serializable
object Saved

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val drawableId: Int
){
    object Home: Screen(
        HOME,
        R.string.rover,
        R.drawable.ic_mars_rover
    )

    object Saved: Screen(
        SAVED,
        R.string.saved,
        R.drawable.ic_save
    )
}