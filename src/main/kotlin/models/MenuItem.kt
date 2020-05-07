package models

/* Custom data type for MenuItems */
data class MenuItem (val itemName: String,
                     val allergens: HashSet<String>,
                     val itemType: ItemType,
                     val itemCategory: String) {

    fun hasAllergen(allergen: String): Boolean {
        return allergen in allergens
    }

    fun getAllergenString(): String {
        return allergens.joinToString(separator = ", ")
    }
}