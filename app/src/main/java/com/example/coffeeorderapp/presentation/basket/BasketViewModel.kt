package com.example.coffeeorderapp.presentation.basket

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.coffeeorderapp.data.db.items.CoffeeItem

class BasketViewModel : ViewModel() {
    private val _basketItems = mutableStateListOf<CoffeeItem>()
    val basketItems: List<CoffeeItem> = _basketItems

    fun addToCart(item: CoffeeItem) {
        _basketItems.add(item)
    }

    fun removeFromCart(item: CoffeeItem) {
        _basketItems.remove(item)
    }
}
