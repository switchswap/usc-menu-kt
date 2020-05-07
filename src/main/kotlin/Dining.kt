import models.DiningHallType
import models.DiningMenu
import models.HallMenu
import parser.MenuParser
import java.io.IOException
import java.util.*

class Dining {
    companion object {
        @JvmStatic
        val menuParser: MenuParser = MenuParser()
    }

    fun getDiningMenu(date: Date): DiningMenu? {
        return kotlin.runCatching {
            menuParser.getDiningMenu(date)
        }.getOrElse {
            println(it.message)
            null
        }
    }

    fun getHallMenu(diningHall: DiningHallType, date: Date): HallMenu? {
        return kotlin.runCatching {
            val diningMenu = menuParser.getDiningMenu(date)
            when (diningHall) {
                DiningHallType.PARKSIDE -> diningMenu.parkside
                DiningHallType.EVK -> diningMenu.evk
                DiningHallType.VILLAGE -> diningMenu.village
            }
        }.getOrElse {
            println(it.message)
            null
        }
    }
}
