package parser

import models.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

/* Class for grabbing and parsing meal data */
class MenuParser {
    /**
     * Scrapes menu data from website and builds [DiningMenu]
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

    private fun buildUrl(date: Date): String{
        // Format date
        var dateString: String = SimpleDateFormat("MM/dd/yyyy").format(date)
        // Remove '0' from start if applicable since this breaks the link otherwise
        dateString = dateString.removePrefix("0")

        // Build url
        return "https://hospitality.usc.edu/residential-dining-menus/?menu_date=$dateString"
    }

    // Fetch menu HTML from url
    @Throws(IOException::class)
    private fun fetchMenu(date: Date) : Document {
        // Fetch menu html
        val url: String = buildUrl(date)
        return Jsoup.connect(url).get()
    }

    // Parse menu HTML into models.MenuItem objects
    private fun parseMenu(menuHTML: Document, date: Date): DiningMenu {
        val diningMenu : DiningMenu = DiningMenu(date)

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

    private fun getItemType(str: String) : ItemType? {
        return when {
            str.contains("breakfast") -> ItemType.BREAKFAST
            str.contains("brunch") -> ItemType.BRUNCH
            str.contains("lunch") -> ItemType.LUNCH
            str.contains("dinner") -> ItemType.DINNER
            else -> null
        }
    }

    private fun getDiningHallType(str: String) : DiningHallType? {
        return when {
            str.contains("everybody's kitchen") -> models.DiningHallType.EVK
            str.contains("parkside restaurant & grill") -> models.DiningHallType.PARKSIDE
            str.contains("usc village dining hall") -> models.DiningHallType.VILLAGE
            else -> null
        }
    }
}