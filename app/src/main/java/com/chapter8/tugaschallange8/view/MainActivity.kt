package com.chapter8.tugaschallange8.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.chapter8.tugaschallange8.datastore.LoginManagerUser
import com.chapter8.tugaschallange8.model.Movie
import com.chapter8.tugaschallange8.R
import com.chapter8.tugaschallange8.ui.theme.TugasChallange8Theme
import com.chapter8.tugaschallange8.viewmodel.ViewModelMovie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TugasChallange8Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModelMovie = viewModel(modelClass = ViewModelMovie::class.java)
                    val dataMovie by viewModelMovie.dataMovieState.collectAsState()
                    val mContex = LocalContext.current
                    val loginManagerUser = LoginManagerUser(mContex)
                    var username by remember{
                        mutableStateOf("")
                    }
                    loginManagerUser.username.asLiveData().observe(this){
                        username = it
                    }

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                            .fillMaxWidth(),
                        ) {
                            Image(
                                painterResource(id = R.drawable.ic_account_),
                                contentDescription = "",
                            )
                            Text(
                                text = "Welcome, $username",
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text(
                                text = "LOGOUT",
                                color = Color.Black,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterVertically)
                                    .clickable {
                                        GlobalScope.launch {
                                            loginManagerUser.logoutLogin()
                                        }
                                        startActivity(Intent(mContex, LoginActivity::class.java))
                                    }
                            )
                        }
                        LazyColumn{
                            if (dataMovie.isEmpty()){
                                item { 
                                    
                                }
                            }else{
                                items(dataMovie){
                                    ListFilm(movie = it)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListFilm(movie: Movie) {
    val gambarBaseUrl = "https://image.tmdb.org/t/p/w500/"
    val mContext = LocalContext.current
    Column(modifier = Modifier.padding(10.dp)) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .clickable {
                    val pindah = Intent(mContext, DetailActivity::class.java)
                    pindah.putExtra("DATAMOVIEW", movie)
                    mContext.startActivity(pindah)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray)
            ) {
                Image(
                    painter = rememberImagePainter(data = gambarBaseUrl + movie.posterPath),
                    contentDescription = "",
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = movie.title,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = movie.releaseDate,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "${movie.voteAverage}",
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TugasChallange8Theme {
    }
}