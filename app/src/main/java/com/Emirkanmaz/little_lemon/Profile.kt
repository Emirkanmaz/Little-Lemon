package com.Emirkanmaz.little_lemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val scrollState = rememberSaveable(saver = ScrollState.Saver) { ScrollState(0) }
    val scope = rememberCoroutineScope()



    val firstNameFlow: Flow<String> = context.dataStore.data.map { it[FIRST_NAME_KEY] ?: "" }
    val lastNameFlow: Flow<String> = context.dataStore.data.map { it[LAST_NAME_KEY] ?: "" }
    val emailFlow: Flow<String> = context.dataStore.data.map { it[EMAIL_KEY] ?: "" }

    val firstName by firstNameFlow.collectAsState("")
    val lastName by lastNameFlow.collectAsState("")
    val email by emailFlow.collectAsState("")




    Scaffold(modifier = Modifier.imePadding()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 100.dp, end = 100.dp, bottom = 20.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = "Personal information",
                modifier = Modifier.padding(top = 100.dp, bottom = 40.dp, start = 15.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "First name",
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B4C51)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 15.dp)
                    .border(1.dp, Color(0xFFC8C8C8), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    text = firstName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Last name",
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B4C51)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 15.dp)
                    .border(1.dp, Color(0xFFC8C8C8), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    text = lastName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Email",
                modifier = Modifier.padding(vertical = 5.dp, horizontal = 15.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B4C51)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 15.dp)
                    .border(1.dp, Color(0xFFC8C8C8), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    text = email,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    fontSize = 14.sp,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))


            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4CE14),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 50.dp)
                    .fillMaxWidth()
                    .padding()
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    scope.launch {
                        removeUserData(context)
                    }
                    navController.navigate(Onboarding.route) {
                        popUpTo(0)
                    }
                }
            ) {
                Text(text = "Log out")
            }
        }
    }
}

private suspend fun removeUserData(context: Context) {
    context.dataStore.edit { preferences ->
        if(preferences.contains(FIRST_NAME_KEY)){
            preferences.remove(FIRST_NAME_KEY)
        }
        if(preferences.contains(LAST_NAME_KEY)){
            preferences.remove(LAST_NAME_KEY)
        }
        if(preferences.contains(EMAIL_KEY)){
            preferences.remove(EMAIL_KEY)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(rememberNavController())

}