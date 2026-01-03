package me.switchswap.diningmenu.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enum representing the meal type of an item.
 */
@Serializable
enum class MealType() {
    @SerialName("Breakfast")
    BREAKFAST,

    @SerialName("Brunch")
    BRUNCH,

    @SerialName("Lunch")
    LUNCH,

    @SerialName("Dinner")
    DINNER
}