package models

/**
 * This class represents the type of an item
 */
enum class ItemType(val typeName: String){
    BREAKFAST("breakfast"),
    BRUNCH("brunch"),
    LUNCH("lunch"),
    DINNER("dinner")
}