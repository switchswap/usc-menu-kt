package me.switchswap.diningmenu.parser

import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import kotlinx.serialization.json.Json
import me.switchswap.diningmenu.models.DiningHallType
import me.switchswap.diningmenu.models.HallMenu
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 *  A class responsible for grabbing and parsing meal data.
 */
class MenuParser(private val client: OkHttpClient = OkHttpClient()) {
    /**
     * Scrapes menu data from the API and builds a [HallMenu] object given a [LocalDate] and
     * [DiningHallType].
     *
     * @throws IOException if there's an error in fetching the menu from the API.
     */
    @Throws(IOException::class)
    fun getDiningMenu(date: LocalDate, diningHallType: DiningHallType): HallMenu {
        val menuJson = fetchMenu(date, diningHallType)
        return Json.decodeFromString(menuJson)
    }

    /**
     * Build the API URL given a [LocalDate] and [DiningHallType].
     */
    private fun buildUrl(date: LocalDate, diningHallType: DiningHallType): String =
        "https://hospitality.usc.edu/wp-json/hsp-api/v1/get-res-dining-menus/" +
                "${diningHallType.getEndpoint()}?y=${date.year}&m=${date.month.number}&d=" +
                "${date.day}"

    /**
     * Fetches menu JSON for a given [LocalDate] and [DiningHallType].
     *
     * @throws IOException If there is an error in fetching the JSON.
     */
    @Throws(IOException::class)
    private fun fetchMenu(date: LocalDate, diningHallType: DiningHallType): String {
        val request = Request.Builder()
            .url(buildUrl(date, diningHallType))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            return response.body.string()
        }
    }

    private companion object {
        fun DiningHallType.getEndpoint(): String {
            return when (this) {
                DiningHallType.EVK -> "evk"
                DiningHallType.PARKSIDE -> "parkside"
                DiningHallType.VILLAGE -> "university-village"
            }
        }

    }
}