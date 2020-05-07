package models

import java.util.*

data class DiningMenu (val date: Date,
                       val parkside: HallMenu = HallMenu(date),
                       val village: HallMenu = HallMenu(date),
                       val evk: HallMenu = HallMenu(date)) {

    fun addItem(menuItem: MenuItem, itemType: ItemType, diningHallType: DiningHallType) {
        when (diningHallType) {
            DiningHallType.PARKSIDE -> parkside.addItem(menuItem, itemType)
            DiningHallType.VILLAGE -> village.addItem(menuItem, itemType)
            DiningHallType.EVK -> evk.addItem(menuItem, itemType)
        }
    }
}