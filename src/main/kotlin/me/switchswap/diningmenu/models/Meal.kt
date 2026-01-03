package me.switchswap.diningmenu.models

import kotlinx.serialization.Serializable

/**
 * Data class representing a meal (breakfast, brunch, lunch, dinner) and its corresponding stations.
 */
@Serializable
data class Meal(
    val name: MealType,
    val stations: List<Station> = emptyList()
)
