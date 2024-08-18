package com.Emirkanmaz.little_lemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.Emirkanmaz.little_lemon.ui.theme.LittleLemonTheme

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val FIRST_NAME_KEY = stringPreferencesKey("first_name")
val LAST_NAME_KEY = stringPreferencesKey("last_name")
val EMAIL_KEY = stringPreferencesKey("email")


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {
                val navController = rememberNavController()
                Navigation(navController = navController)

            }
        }
    }
}


