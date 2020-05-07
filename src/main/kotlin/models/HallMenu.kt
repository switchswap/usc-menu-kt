package models

import java.util.*
import kotlin.collections.HashMap

data class HallMenu (val date: Date,
                     val breakfast: HashMap<String, MenuItem> = HashMap(),
                     val brunch: HashMap<String, MenuItem> = HashMap(),
                     val lunch: HashMap<String, MenuItem> = HashMap(),
                     val dinner: HashMap<String, MenuItem> = HashMap()) {

    fun hasBreakfast(): Boolean {
        return !breakfast.isEmpty()
    }

    fun hasBrunch(): Boolean {
        return !brunch.isEmpty()
    }

    fun hasLunch(): Boolean {
        return !lunch.isEmpty()
    }

    fun hasDinner(): Boolean {
        return !dinner.isEmpty()
    }

    fun isClosed(): Boolean {
        return breakfast.isEmpty() && brunch.isEmpty() && lunch.isEmpty() && dinner.isEmpty()
    }

    fun addItem(menuItem: MenuItem, itemType: ItemType) {
        val itemName: String = menuItem.itemName.toLowerCase()
        when (itemType) {
            ItemType.BREAKFAST -> breakfast[itemName] = menuItem
            ItemType.BRUNCH -> brunch[itemName] = menuItem
            ItemType.LUNCH -> lunch[itemName] = menuItem
            ItemType.DINNER -> dinner[itemName] = menuItem
        }
    }
}