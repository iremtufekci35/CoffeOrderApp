package com.example.coffeeorderapp.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.coffeeorderapp.data.db.items.CoffeeItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

object BasketDataStore {
    private val Context.dataStore by preferencesDataStore(name = "basket")
    private val BASKET_KEY = stringPreferencesKey("basket_items")
    private val gson = Gson()

    suspend fun saveBasket(context: Context, items: List<CoffeeItem>) {
        val json = gson.toJson(items)
        context.dataStore.edit {
            it[BASKET_KEY] = json
        }
    }

    suspend fun loadBasket(context: Context): List<CoffeeItem> {
        val prefs = context.dataStore.data.first()
        val json = prefs[BASKET_KEY] ?: return emptyList()
        return gson.fromJson(json, Array<CoffeeItem>::class.java).toList()
    }
    suspend fun removeItemFromBasket(context: Context, item: CoffeeItem) {
        val currentList = loadBasket(context).toMutableList()
        currentList.removeIf { it.name == item.name && it.price == item.price }
        saveBasket(context, currentList)
    }

}
