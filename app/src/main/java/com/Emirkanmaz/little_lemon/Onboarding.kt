package com.Emirkanmaz.little_lemon

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch


@Composable
fun Onboarding(navController: NavHostController) {

    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }


    var isEmailValid by rememberSaveable {mutableStateOf(true)}
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberSaveable(saver = ScrollState.Saver) { ScrollState(0) }

    Scaffold(modifier = Modifier.imePadding()) { innerPadding ->
        Column(modifier = Modifier
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
                "Let's get to know you",
                modifier = Modifier
                    .background(color = Color(0xFF495E57))
                    .padding(50.dp)
                    .fillMaxWidth(),
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Personal information",
                modifier = Modifier.padding(vertical = 50.dp, horizontal = 15.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First Name") },
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last Name") },
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },
                label = { Text(text = "Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = !isEmailValid,
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 5.dp)
                    .fillMaxWidth()
            )

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF4CE14), contentColor = Color.Black),
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 50.dp)
                    .fillMaxWidth()
                    .padding()
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && isEmailValid) {
                        scope.launch {
                            saveUserData(context, firstName, lastName, email)
                        }
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_LONG).show()
                        navController.navigate(Home.route) {
                            popUpTo(0)
                        }

                    }else {
                        Toast.makeText(
                            context,
                            "Registration unsuccessful. Please enter all data.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            ) {
                Text(text = "Register")
            }
        }
    }

}

private suspend fun saveUserData(context: Context, firstName: String, lastName: String, email: String) {
    context.dataStore.edit { preferences ->
        preferences[FIRST_NAME_KEY] = firstName
        preferences[LAST_NAME_KEY] = lastName
        preferences[EMAIL_KEY] = email
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding(rememberNavController())

}