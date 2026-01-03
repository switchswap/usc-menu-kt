package me.switchswap.diningmenu

import kotlinx.datetime.LocalDate
import me.switchswap.diningmenu.models.DiningHallType
import me.switchswap.diningmenu.models.HallMenu
import me.switchswap.diningmenu.parser.MenuParser
import java.io.IOException

/**
 *  Front-end of library that wraps around the internal [MenuParser] class.
 */
class Dining(private val menuParser: MenuParser = MenuParser()) {

    /**
     * Retrieves the menu for a given [DiningHallType] and [LocalDate].
     *
     * @throws IOException if there's an error in fetching the menu from the API.
     */
    @Throws(IOException::class)
    fun getHallMenu(diningHall: DiningHallType, date: LocalDate): HallMenu {
        return menuParser.getDiningMenu(date, diningHall)
    }
}
