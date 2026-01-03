package me.switchswap.diningmenu.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a station at a dining hall which serves a [List] of [MenuItem]s.
 */
@Serializable
data class Station(
    @SerialName("station")
    val name: String,
    val subtitle: String,
    val menu: List<MenuItem>
)
