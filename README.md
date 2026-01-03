# USC-Menu-kt
A USC Dining Hall Menu API for Kotlin!

Wow, that's a lot to say!

[![](https://jitpack.io/v/switchswap/usc-menu-kt.svg)](https://jitpack.io/#switchswap/usc-menu-kt)
---

## Installation
<details>
<summary>Gradle</summary>

1. Add this in your settings.gradle.kts at the end of repositories

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

2. Add the dependency

```kotlin
dependencies {
    implementation("com.github.switchswap:usc-menu-kt:v3.0.0")
}
```
</details>

<details>
<summary>Maven</summary>

1. Add the JitPack repository to your build file 

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

2. Add the dependency
```xml
<dependency>
    <groupId>com.github.switchswap</groupId>
    <artifactId>usc-menu-kt</artifactId>
    <version>v3.0.0</version>
</dependency>
```
</details>

## Usage
The API has been updated to use `kotlinx.datetime.LocalDate` and now fetches a menu for a specific dining hall, mirroring the USC Hospitality API.

```kotlin
// Create API object
val dining = Dining()

// Generate a date object
val date = LocalDate(2025, 12, 3)

// Pass the dining hall and date into the function
val hallMenu = dining.getHallMenu(DiningHallType.PARKSIDE, date) 

// The returned HallMenu object contains a list of meals.
// Each meal contains a list of stations, which in turn contains a list of menu items.
// Let's print all items for all meals at Parkside for the given date:
hallMenu.meals.forEach { meal ->
    println("--- ${meal.name} ---")
    meal.stations.forEach { station ->
        println("  -- ${station.name} --")
        station.menu.forEach { menuItem ->
            println("    - ${menuItem.name}")
        }
    }
}
//Ouput:
//--- BREAKFAST ---
//    -- Hot Line --
//        - MADE TO ORDER OMELETES
//        - S'mores French Toast Bake
//        - Soyrizo, Spinach and Vegan Cheese Quesadilla
//        - Scrambled Eggs
//        - Bacon
//        - Boiled Eggs
//        - Triangle Hash Browns
//        - Chicken Andouille Sausage
//    -- Fresh from the Farm --
//        - Plain Greek Yogurt
//        - Strawberry Yogurt
//        ...
```
