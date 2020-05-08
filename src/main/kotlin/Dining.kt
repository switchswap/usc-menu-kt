import models.DiningHallType
import models.DiningMenu
import models.HallMenu
import parser.MenuParser
import java.io.IOException
import java.lang.IllegalArgumentException
import java.util.*

/**
 *  Front-end of library that wraps around the internal [MenuParser] class
 */
class Dining {
    companion object {
        @JvmStatic
        val menuParser: MenuParser = MenuParser()
    }

    @Throws(IOException::class, IllegalArgumentException::class)
    fun getDiningMenu(date: Date): DiningMenu {
        return menuParser.getDiningMenu(date)
    }

    @Throws(IOException::class, IllegalArgumentException::class)
    fun getHallMenu(diningHall: DiningHallType, date: Date): HallMenu {
        val diningMenu = menuParser.getDiningMenu(date)
        return when (diningHall) {
            DiningHallType.PARKSIDE -> diningMenu.parkside
            DiningHallType.EVK -> diningMenu.evk
            DiningHallType.VILLAGE -> diningMenu.village
        }
    }
}
