package models

import java.util.*
import kotlin.collections.HashMap

/**
 * This class represents a full menu for a given day
 */
data class DiningMenu (val date: Date, val halls: HashMap<String, HallMenu> = HashMap()) {

    val parkside: HallMenu = halls.getOrPut(DiningHallType.PARKSIDE.name) { HallMenu(date, DiningHallType.PARKSIDE) }
    val village: HallMenu = halls.getOrPut(DiningHallType.VILLAGE.name) { HallMenu(date, DiningHallType.VILLAGE) }
    val evk: HallMenu = halls.getOrPut(DiningHallType.EVK.name) { HallMenu(date, DiningHallType.EVK) }

    /**
     * Add item to the dining menu
     *
     * @param menuItem The [MenuItem] to add
     * @param itemType The [ItemType] of the item
     * @param diningHallType The [DiningHallType] of the item
     */
    fun addItem(menuItem: MenuItem, itemType: ItemType, diningHallType: DiningHallType) {
        when (diningHallType) {
            DiningHallType.PARKSIDE -> parkside.addItem(menuItem, itemType)
            DiningHallType.VILLAGE -> village.addItem(menuItem, itemType)
            DiningHallType.EVK -> evk.addItem(menuItem, itemType)
        }
    }
}