package com.example.coffeeorderapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.coffeeorderapp.data.db.items.CoffeeItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import com.example.coffeeorderapp.data.local.BasketDataStore
import com.example.coffeeorderapp.presentation.basket.BasketScreen

@Composable
fun HomeScreen() {
    val tabs = listOf("Sıcak", "Soğuk")
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val basketItems = remember { mutableStateListOf<CoffeeItem>() }
    var selectedScreen by remember { mutableStateOf("Ana Sayfa") }

    val hotCoffees = listOf(
        CoffeeItem("Latte", "₺40", "https://images.unsplash.com/photo-1511920170033-f8396924c348"),
        CoffeeItem("Espresso", "₺30", "https://images.unsplash.com/photo-1587732440609-1965a3a5c7b2"),
        CoffeeItem("Cappuccino", "₺35", "https://images.unsplash.com/photo-1527168020762-3f3b5d2f3c91")
    )
    val coldCoffees = listOf(
        CoffeeItem("Mocha", "₺42", "https://images.unsplash.com/photo-1524592321522-6a9f7e640420"),
        CoffeeItem("Americano", "₺33", "https://images.unsplash.com/photo-1605478201968-38f3be0349a1")
    )

    val currentList = if (selectedTabIndex == 0) hotCoffees else coldCoffees

    val context = LocalContext.current

    LaunchedEffect(basketItems) {
        BasketDataStore.saveBasket(context, basketItems)
    }

    LaunchedEffect(Unit) {
        val savedItems = BasketDataStore.loadBasket(context)
        basketItems.clear()
        basketItems.addAll(savedItems)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                selectedScreen = selectedScreen,
                onTabSelected = { selectedScreen = it }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ) {
            when (selectedScreen) {
                "Ana Sayfa" -> {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        edgePadding = 16.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = { Text(text = title) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    CoffeeGrid(coffees = currentList, onAddToCart = { item ->
                        basketItems.add(item)
                    })
                }

                "Sepetim" -> {
                    BasketScreen()
                }

                "Hesabım" -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Hesabım Sayfası")
                    }
                }
                "Favorilerim" -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Favorilerim Sayfası")
                    }
                }
            }
        }
    }
}

@Composable
fun CoffeeGrid(coffees: List<CoffeeItem>, onAddToCart: (CoffeeItem) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(coffees) { item ->
            CoffeeCard(item = item, onAddToCart = onAddToCart)
        }
    }
}

@Composable
fun CoffeeCard(item: CoffeeItem, onAddToCart: (CoffeeItem) -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                        .height(120.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = item.price,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        onAddToCart(item)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Sepete Ekle",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sepete Ekle")
                }
            }

            IconButton(
                onClick = { isFavorite = !isFavorite },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favori",
                    tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedScreen: String,
    onTabSelected: (String) -> Unit
) {
    val items = listOf("Ana Sayfa","Favorilerim","Sepetim", "Hesabım")

    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = selectedScreen == item,
                onClick = { onTabSelected(item) },
                icon = {
                    when (item) {
                        "Ana Sayfa" -> Icon(Icons.Default.Home, contentDescription = "Ana Sayfa")
                        "Sepetim" -> Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Sepetim"
                        )

                        "Hesabım" -> Icon(Icons.Default.Person, contentDescription = "Hesabım")
                        "Favorilerim" -> Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorilerim"
                        )
                    }
                },
                label = null,
                alwaysShowLabel = false
            )
        }}
}





