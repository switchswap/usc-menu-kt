package models

import java.util.*
import kotlin.collections.HashMap

/**
 * This class represents a dining hall menu
 */
data class HallMenu(val date: Date, val hallType: DiningHallType,
                    val menus: HashMap<String, HashMap<String, MenuItem>> = HashMap()) {

    val breakfast: HashMap<String, MenuItem> = menus.getOrPut(ItemType.BREAKFAST.typeName) { HashMap() }
    val brunch: HashMap<String, MenuItem> = menus.getOrPut(ItemType.BRUNCH.typeName) { HashMap() }
    val lunch: HashMap<String, MenuItem> = menus.getOrPut(ItemType.LUNCH.typeName) { HashMap() }
    val dinner: HashMap<String, MenuItem> = menus.getOrPut(ItemType.DINNER.typeName) { HashMap() }

    fun hasBreakfast(): Boolean {
        return breakfast.isNotEmpty()
    }

    fun hasBrunch(): Boolean {
        return brunch.isNotEmpty()
    }

    fun hasLunch(): Boolean {
        return lunch.isNotEmpty()
    }

    fun hasDinner(): Boolean {
        return dinner.isNotEmpty()
    }

    fun isClosed(): Boolean {
        return breakfast.isEmpty() && brunch.isEmpty() && lunch.isEmpty() && dinner.isEmpty()
    }

    /**
     * Add item to dining hall menu
     *
     * @param menuItem The [MenuItem] to add
     * @param itemType The [ItemType] of the item
     */
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