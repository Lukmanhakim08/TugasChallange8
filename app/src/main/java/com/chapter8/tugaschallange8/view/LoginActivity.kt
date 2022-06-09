package com.chapter8.tugaschallange8.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chapter8.tugaschallange8.R
import com.chapter8.tugaschallange8.datastore.LoginManagerUser
import com.chapter8.tugaschallange8.ui.theme.TugasChallange8Theme
import com.chapter8.tugaschallange8.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasChallange8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mContext = LocalContext.current
                    val loginManagerUser = LoginManagerUser(mContext)
                    loginManagerUser.boolean.asLiveData().observe(this){
                        if (it == true){
                            mContext.startActivity(Intent(mContext, MainActivity::class.java))
                        }
                    }
                    LoginUserInterface()
                }
            }
        }
    }
}

@Composable
fun LoginUserInterface() {
    val mContext = LocalContext.current
    val viewModelUser = viewModel(modelClass = ViewModelUser::class.java)
    val dataUser by viewModelUser.dataUserState.collectAsState()
    val loginManagerUser = LoginManagerUser(mContext)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var passwordVisible by rememberSaveable {
            mutableStateOf(false)
        }
        Image(
            painter = painterResource(id = R.drawable.logo_splash),
            contentDescription = "img",
            modifier = Modifier.padding(bottom = 20.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Masukkan email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Input password") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, description)
                }
            }
        )
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    for (i in dataUser.indices) {
                        if (email == dataUser[i].email && password == dataUser[i].password) {
                            GlobalScope.launch {
                                loginManagerUser.setBoolean(true)
                                loginManagerUser.saveDataLogin(
                                    dataUser[i].alamat,
                                    dataUser[i].email,
                                    dataUser[i].name,
                                    dataUser[i].password,
                                    dataUser[i].tanggal_lahir,
                                    dataUser[i].username
                                )
                            }
                            Toast.makeText(mContext, "Login berhasil", Toast.LENGTH_SHORT).show()
                            mContext.startActivity(Intent(mContext, MainActivity::class.java))
                        } else if (i == dataUser.lastIndex && email != dataUser[i].email && password != dataUser[i].password) {
                            Toast.makeText(
                                mContext,
                                "Email/password tidak sesuai",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "Login")
        }
        Text(
            text = "Belum punya akun, Register",
            Modifier.clickable {
                mContext.startActivity(
                    Intent(
                        mContext,
                        RegisterActivity::class.java
                    )
                )
            })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview3() {
    TugasChallange8Theme {
        LoginUserInterface()
    }
}