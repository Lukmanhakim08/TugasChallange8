package com.chapter8.tugaschallange8

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chapter8.tugaschallange8.ui.theme.TugasChallange8Theme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasChallange8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterUserInterface()
                }
            }
        }
    }
}

@Composable
fun RegisterUserInterface() {
    val mContext = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var username by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var konfirmasiPasswrod by remember {
            mutableStateOf("")
        }

        Image(
            painter = painterResource(id = R.drawable.logo_splash),
            contentDescription = "img",
            modifier = Modifier.padding(bottom = 20.dp)
        )
        TextField(value = username,
            onValueChange = {username = it},
            label = { Text(text = "Masukkan username")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(value = email,
            onValueChange = { email = it },
            label = { Text(text = "Masukkan email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(value = password,
            onValueChange = { password = it },
            label = { Text(text = "Masukkan password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(
            value = konfirmasiPasswrod,
            onValueChange = { konfirmasiPasswrod = it },
            label = { Text(text = "Masukkan konfirmasi password") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            Toast.makeText(mContext, "Register berhasil", Toast.LENGTH_SHORT).show()
            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
        }, modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "REGISTER")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    TugasChallange8Theme {
        RegisterUserInterface()
    }
}