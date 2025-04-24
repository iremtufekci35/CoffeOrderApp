package com.example.coffeeorderapp.presentation.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.coffeeorderapp.data.db.items.CoffeeItem
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import com.example.coffeeorderapp.data.local.BasketDataStore

@Composable
fun BasketScreen() {
    val context = LocalContext.current
    val basketItems = remember { mutableStateListOf<CoffeeItem>() }

    LaunchedEffect(Unit) {
        val loadedItems = BasketDataStore.loadBasket(context)
//        basketItems.clear()
        basketItems.addAll(loadedItems)
    }

    Column {
        if (basketItems.isEmpty()) {
            Text("Sepetiniz boş.")
        } else {
            basketItems.forEach { item ->
                BasketItemCard(
                    item = item,
                    onDelete = {
//                        LaunchedEffect(item) {
//                            BasketDataStore.removeItemFromBasket(context, item)
//                            basketItems.remove(item) // Listeyi güncelle
//                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BasketItemCard(
    item: CoffeeItem,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageRes)
                    .crossfade(true)
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, fontWeight = FontWeight.Bold)
                Text(text = item.price, color = MaterialTheme.colorScheme.primary)
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
