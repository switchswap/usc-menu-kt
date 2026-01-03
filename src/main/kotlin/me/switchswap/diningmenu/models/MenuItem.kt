package me.switchswap.diningmenu.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *  This class represents a singular menu item from the dining hall API.
 */
@Serializable
data class MenuItem(
    @SerialName("item")
    val name: String,
    val allergens: Set<String>,
    @SerialName("dietary_preferences")
    val dietaryPreferences: Set<String>,
    val preferences: Set<String>
)
