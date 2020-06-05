# USC-Menu-kt
A USC Dining Hall Menu API for Kotlin!

Wow, that's a lot to say!

[![](https://jitpack.io/v/switchswap/usc-menu-kt.svg)](https://jitpack.io/#switchswap/usc-menu-kt)
---

## Installation
<details>
<summary>Gradle</summary>

1. Add this to your root `build.gradle` at the end of repositories

```kotlin
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency

```kotlin
dependencies {
    implementation 'com.github.switchswap:usc-menu-kt:0.0.1'
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
    <version>0.0.1</version>
</dependency>
```
</details>

## Usage
```kotlin
// Create API object
val dining = Dining()

// Generate a date object
val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
val dateString = "02/21/2020"
var date: Date = simpleDateFormat.parse(dateString)

// Pass into function
var diningMenu = dining.getDiningMenu(date) // Return full dining hall menu object

// Print all breakfast items at Parkside dining hall for the given date
for (menuItem in diningMenu.parkside.breakfast) {
    // MenuItems are stored as a HashMap with name as the key and the object as the value
    // This is for fast lookups of items
    println(menuItem.value.itemName) // Here we reference the name from object instead of just using the key
}
```
