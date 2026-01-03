package me.switchswap.diningmenu.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import me.switchswap.diningmenu.parser.LocalDateSerializer

/**
 * Data class representing a dining hall menu.
 */
@Serializable
data class HallMenu(
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,
    val location: DiningHallType,
    val meals: List<Meal>
) {
    /**
     * Checks if the menu includes a brunch with at least one station.
     */
    fun hasBrunch(): Boolean = meals.any { it.name == MealType.BRUNCH && it.stations.isNotEmpty() }
}
