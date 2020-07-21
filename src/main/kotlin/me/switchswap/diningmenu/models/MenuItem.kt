package me.switchswap.diningmenu.models

/**
 *  This class represents a singular menu item
 */
data class MenuItem (val itemName: String,
                     val allergens: HashSet<String>,
                     val itemType: ItemType,
                     val itemCategory: String) {

    /**
     * Check if item has a given allergen
     *
     * @param allergen The allergen to check for
     */
    fun hasAllergen(allergen: String): Boolean {
        return allergen in allergens
    }

    /**
     * Get all the allergens as a string
     */
    fun getAllergenString(): String {
        return allergens.joinToString(separator = ", ")
    }
}