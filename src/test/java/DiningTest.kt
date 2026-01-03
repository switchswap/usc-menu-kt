package me.switchswap.diningmenu

import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.LocalDate
import me.switchswap.diningmenu.models.DiningHallType
import me.switchswap.diningmenu.models.MealType
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class DiningTest {
    private val dining = Dining()

    @ParameterizedTest
    @EnumSource(DiningHallType::class)
    fun `getHallMenu returns menu for a valid date`(diningHall: DiningHallType) {
        val date = LocalDate(2025, 12, 3)

        val menu = dining.getHallMenu(diningHall, date)

        assertThat(menu).isNotNull()
        assertThat(menu.location).isEqualTo(diningHall)
        assertThat(menu.date).isEqualTo(date)
        assertThat(menu.meals).isNotEmpty()
        for (meal in menu.meals) {
            // No brunch on this day so ignore
            if (meal.name == MealType.BRUNCH) continue
            assertThat(meal.stations).isNotEmpty()
        }
    }

    @Test
    fun `getHallMenu for parkside has brunch`() {
        // Weekends have brunch
        val date = LocalDate(2025, 12, 6)
        val diningHall = DiningHallType.PARKSIDE

        val menu = dining.getHallMenu(diningHall, date)

        assertThat(menu.hasBrunch()).isTrue()
    }
}
