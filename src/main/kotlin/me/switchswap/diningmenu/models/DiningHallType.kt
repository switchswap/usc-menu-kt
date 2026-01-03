package me.switchswap.diningmenu.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enum class representing the name of a dining hall.
 */
@Serializable
enum class DiningHallType(val id: String) {
    @SerialName("everybodys-kitchen")
    EVK("everybodys-kitchen"),

    @SerialName("parkside-restaurant-grill")
    PARKSIDE("parkside-restaurant-grill"),

    @SerialName("usc-village-dining-hall")
    VILLAGE("usc-village-dining-hall")
}