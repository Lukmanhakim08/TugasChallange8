package com.chapter8.tugaschallange8.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chapter8.tugaschallange8.ui.theme.TugasChallange8Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val dataFilm = FilmRepository().getAllFilm()

        setContent {
            TugasChallange8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ListFilm()
//                    LazyColumn{
//                        items(items = dataFilm){ item: Film ->
//                            ListFilm(film = item)
//                        }
//                    }
                }
            }
        }
    }
}

@Composable
fun ListFilm() {
    Column(modifier = Modifier.padding(10.dp)) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(color = Color.Gray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Judul Film",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Nama Sutradara",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Text(
                    text = "Description Film",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TugasChallange8Theme {
        ListFilm()
    }
}