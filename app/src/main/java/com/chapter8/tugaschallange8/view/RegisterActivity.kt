package com.chapter8.tugaschallange8.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chapter8.tugaschallange8.R
import com.chapter8.tugaschallange8.model.RequestUser
import com.chapter8.tugaschallange8.ui.theme.TugasChallange8Theme
import com.chapter8.tugaschallange8.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
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
    val mDay: Int
    val mYear: Int
    val mMonth: Int
    val mCalendar = Calendar.getInstance()
    val userViewModel = viewModel(modelClass = ViewModelUser::class.java)

    //current year
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        var username by remember {
            mutableStateOf("")
        }
        var password by rememberSaveable {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var konfirmasiPasswrod by rememberSaveable {
            mutableStateOf("")
        }
        var passwordVisible by rememberSaveable{
            mutableStateOf(false)
        }

        var konfirmasiPasswordVisible by rememberSaveable{
            mutableStateOf(false)
        }
        var name by remember {
            mutableStateOf("")
        }
        var tanggalLahir by remember {
            mutableStateOf("")
        }
        var alamat by remember {
            mutableStateOf("")
        }
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                tanggalLahir = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
        )
        Image(
            painter = painterResource(id = R.drawable.logo_splash),
            contentDescription = "img",
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Input nama") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        TextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text(text = "Input alamat") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        Text(
            text = "Tanggal lahir(dd/mm/yy):  $tanggalLahir",
            fontSize = 20.sp,
            modifier = Modifier.background(color = Color.Gray)
        )
        Button(onClick = { mDatePickerDialog.show() }) {
            Text(text = "Pilih tanggal lahir")
        }
        TextField(
            value = username,
            onValueChange = {username = it},
            label = { Text(text = "Masukkan username")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
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
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        TextField(
            value = konfirmasiPasswrod,
            onValueChange = { konfirmasiPasswrod = it },
            label = { Text(text = "Input konfirmasi password") },
            singleLine = true,
            visualTransformation = if(konfirmasiPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (konfirmasiPasswordVisible)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description = if (konfirmasiPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = {konfirmasiPasswordVisible = !konfirmasiPasswordVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        )

        Button(
            onClick = {
                      userViewModel.insertNewUSer(
                          RequestUser(
                              alamat,
                              email,
                              "http://placeimg.com/640/480",
                              name,
                              password,
                              tanggalLahir,
                              username
                          )
                      )
                Toast.makeText(mContext, "Register berhasil", Toast.LENGTH_SHORT).show()
                mContext.startActivity(Intent(mContext, LoginActivity::class.java))
            }, modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
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