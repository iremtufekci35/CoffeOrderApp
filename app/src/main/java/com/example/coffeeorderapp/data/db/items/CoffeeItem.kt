package com.example.coffeeorderapp.data.db.items

data class CoffeeItem(
    val name: String,
    val price: String,
    val imageRes: String,
    var isFavorite: Boolean = false
)
