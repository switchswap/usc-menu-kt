package me.switchswap.diningmenu.parser

import me.switchswap.diningmenu.exceptions.InvalidHallTypeException
import me.switchswap.diningmenu.exceptions.InvalidItemTypeException
import me.switchswap.diningmenu.models.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

/**
 *  This class is for grabbing and parsing meal data
 */
class MenuParser {
    /**
     * Scrapes menu data from website and builds [DiningMenu]
     *
     * @param date specifies which day's menu data to return
     * @return [DiningMenu]
     * @throws IOException if there's an error in fetching the menu HTML
     */
    @Throws(IOException::class)
    fun getDiningMenu(date: Date): DiningMenu {
        // Fetch html
        val menuHTML: Document = fetchMenu(date)
        return parseMenu(menuHTML, date)
    }

    /**
     * Build URL given a date
     *
     * @param date to build for
     * @return URL string of menu page
     */
    private fun buildUrl(date: Date): String{
        // Format date
        var dateString: String = SimpleDateFormat("MM/dd/yyyy").format(date)
        // Remove '0' from start if applicable since this breaks the link otherwise
        dateString = dateString.removePrefix("0")

        // Build url
        return "https://hospitality.usc.edu/residential-dining-menus/?menu_date=$dateString"
    }

    /**
     * Fetches menu HTML for a given date
     *
     * @param date The date to fetch for
     * @throws IOException If there is an error in fetching the HTML
     */
    @Throws(IOException::class)
    private fun fetchMenu(date: Date) : Document {
        // Fetch menu html
        val url: String = buildUrl(date)
        return Jsoup.connect(url).get()
    }

    /**
     * Parses menu HTML into [DiningMenu] object
     *
     * @param menuHTML HTML of page to parse
     * @param date Date of menu
     * @throws IllegalArgumentException If the me.switchswap.diningmenu.parser receives any invalid data
     */
    @Throws(IOException::class, InvalidHallTypeException::class, InvalidItemTypeException::class)
    private fun parseMenu(menuHTML: Document, date: Date): DiningMenu {
        val diningMenu = DiningMenu(date)

        // Get the meal types (Breakfast, Brunch, Lunch, and Dinner)
        val itemTypeElements = menuHTML.select("div.hsp-accordian-container")
        itemTypeElements.forEach { mealTypeElement ->
            val itemType: ItemType? = getItemType(mealTypeElement
                    .select("h2 > span.fw-accordion-title-inner")
                    .first()
                    .text()
                    .toLowerCase())

            // For each meal type, get each dining hall
            val diningHallElements = mealTypeElement.select("div.col-sm-6.col-md-4")
            diningHallElements.forEach {diningHallElement ->
                val diningHallType: DiningHallType? = getDiningHallType(diningHallElement
                        .select("h3.menu-venue-title")
                        .first()
                        .text()
                        .toLowerCase())

                // Grab menu subcategories
                val subcategoryElements = diningHallElement.select("h4")

                // Grab menu list following the subcategory
                // The css query could be "h4 + ul.menu-item-list" to guarantee that this list follows a category
                // but it may not be necessary unless the menu format becomes inconsistent in the future
                val menuItemLists = diningHallElement.select("ul.menu-item-list")

                // For each subcategory list, get all items
                menuItemLists.forEachIndexed {index, menuItemList ->
                    val itemCategory: String = subcategoryElements[index].text()

                    val menuItemElements = menuItemList.select("li")
                    menuItemElements.forEach {menuItemElement ->
                        val itemName = menuItemElement.ownText()

                        // For each item, get all allergens
                        val allergens = HashSet<String>()
                        val mealItemAllergenElements = menuItemElement.select("span.fa-allergen-container > i > span")
                        mealItemAllergenElements.forEach{ menuItemAllergenElement ->
                            allergens.add(menuItemAllergenElement.text().toLowerCase())
                        }

                        // This will always be true since the functions either return or throw an exception
                        // Leaving this in if I decide to change the underlying implementation later
                        if (itemType != null && diningHallType != null) {
                            // Item complete
                            val menuItem = MenuItem(itemName, allergens, itemType, itemCategory)
                            diningMenu.addItem(menuItem, itemType, diningHallType)
                        }
                    }
                }
            }
        }
        return diningMenu
    }

    /**
     * Get the [ItemType] enum from a string
     *
     * @param type The string to parse
     */
    // TODO: There has to be an existing enum function that trivializes this.
    @Throws(InvalidItemTypeException::class)
    private fun getItemType(type: String) : ItemType {
        return when {
            type.contains("breakfast") -> ItemType.BREAKFAST
            type.contains("brunch") -> ItemType.BRUNCH
            type.contains("lunch") -> ItemType.LUNCH
            type.contains("dinner") -> ItemType.DINNER
            else -> throw InvalidItemTypeException("Invalid ItemType \"$type\"!")
        }
    }

    /**
     * Get the [DiningHallType] enum from a string
     *
     * @param type The string to parse
     */
    @Throws(InvalidHallTypeException::class)
    private fun getDiningHallType(type: String) : DiningHallType {
        return when {
            type.contains("everybody's kitchen") -> me.switchswap.diningmenu.models.DiningHallType.EVK
            type.contains("parkside restaurant & grill") -> me.switchswap.diningmenu.models.DiningHallType.PARKSIDE
            type.contains("usc village dining hall") -> me.switchswap.diningmenu.models.DiningHallType.VILLAGE
            else -> throw InvalidHallTypeException("Invalid DiningHallType \"$type\"!")
        }
    }
}