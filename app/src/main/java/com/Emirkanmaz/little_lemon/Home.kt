package com.Emirkanmaz.little_lemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.Emirkanmaz.little_lemon.ui.theme.cloud
import com.Emirkanmaz.little_lemon.ui.theme.green
import com.Emirkanmaz.little_lemon.ui.theme.yellow
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun Home(navController: NavHostController, database: MenuItemDao.AppDatabase) {
    val menuItemsDatabase by database.menuItemDao().getAll().observeAsState(emptyList())
    Column() {
        TopAppBar(navController)
        HeroSection(menuItemsDatabase)
    }
}

@Composable
fun TopAppBar(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(1f)) // Left spacer
            Image(
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.weight(1f)) // Right spacer
            Image(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable { navController.navigate(Profile.route) }
                    .padding(end = 15.dp),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile Picture"
            )
        }
    }
}

@Composable
fun HeroSection(menuItemsDatabase: List<MenuItemRoom>) {
    var menuItems = menuItemsDatabase
    var searchPhrase by remember {
        mutableStateOf("")
    }
    var selectedCategory by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(green)
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = yellow
        ) // Text
        Text(
            text = stringResource(id = R.string.location),
            fontSize = 24.sp,
            color = cloud
        ) // Text
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.description),
                style = MaterialTheme.typography.bodyLarge,
                color = cloud,
                modifier = Modifier
                    .padding(bottom = 28.dp, end = 20.dp)
                    .fillMaxWidth(0.6f)
            ) // Text
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Upper Panel Image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            ) // Image
        } // Row

        TextField(
            placeholder = { Text(text = "Enter Search Phrase") },
            value = searchPhrase,
            onValueChange = { searchPhrase = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .height(60.dp)
                .clip(RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.White),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
        )
        if (searchPhrase.isNotEmpty()) {
            menuItems =
                menuItemsDatabase.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "ORDER FOR DELIVERY!",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 15.dp)
        )
        val scrollState = rememberScrollState()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .horizontalScroll(scrollState),
        ) {
            Button(
                onClick = { selectedCategory = "" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDEFEE),
                    contentColor = Color.Black),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(text = "All", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { selectedCategory = "starters" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDEFEE),
                    contentColor = Color.Black),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(text = "Starters", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { selectedCategory = "mains" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDEFEE),
                    contentColor = Color.Black),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(text = "Mains", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { selectedCategory = "desserts" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDEFEE),
                    contentColor = Color.Black),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(text = "Desserts", fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = { selectedCategory = "drinks" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEDEFEE),
                    contentColor = Color.Black),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) {
                Text(text = "Drinks", fontWeight = FontWeight.Bold)
            }
        }
    }
    if (searchPhrase.isNotEmpty()) {
        menuItems =
            menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
    }
    if (selectedCategory.isNotEmpty()) {
        menuItems =
            menuItems.filter { it.category.contains(selectedCategory, ignoreCase = true) }
    }

    MenuItemsList(items = menuItems)
}

@Composable
fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 10.dp)
    ) {
        items(
            items = items,
            itemContent = { menuItem -> MenuItem(menuItem) }
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItemRoom) {
    HorizontalDivider(thickness = 0.5.dp, color = Color.Gray, modifier = Modifier.padding(5.dp))
    Column(
        modifier = Modifier.padding(horizontal = 15.dp)
    ) {
        Text(text = menuItem.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = menuItem.description,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$${menuItem.price}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }
            GlideImage(
                model = menuItem.image, contentDescription = menuItem.title,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .size(100.dp),
                contentScale = ContentScale.Crop
            )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
//    Home(rememberNavController(), MenuItemDao.AppDatabase.)
}