package com.example.mvvm

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.mvvm.ui.theme.MvvMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MvvM : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          MvvMTheme {
              val viewModel = hiltViewModel<MvvMViewModel>()
              val context = LocalContext.current
              Surface(modifier = Modifier.fillMaxSize(),
              color = MaterialTheme.colors.background) {
                  // ref to my userstates inside view model
                  val userstate by viewModel.userState.collectAsState()
                  // use when statements to toggle the appearance or update view
                  // ... with regards to state
                  when(userstate){
                      is UserState.LoadingState -> {
                          Box(modifier = Modifier.fillMaxSize(),
                          contentAlignment = Alignment.Center
                          ){
                              CircularProgressIndicator()
                          }
                      }
                      is UserState.Success -> {
                          val users = (userstate as UserState.Success).users
                          UserListView(users)
                      }
                      is UserState.Error -> {
                          val message = (userstate as UserState.Error).errorMessage
                          Toast.makeText(context, message,Toast.LENGTH_SHORT).show()

                      }
                      else -> {
                          Toast.makeText(context, "Error occurred, Kindly reset and try again", Toast.LENGTH_SHORT)
                              .show()
                      }
                  }

              }


          }
        }
    }
}

@Composable
fun UserListView(users: List<User>){
    LazyColumn{
        items(users){
            UserItem(user = it)
        }
    }
}

@Composable
fun UserItem(user: User){
    Row(modifier = Modifier.padding(10.dp)){
        Box(modifier = Modifier
            .size(50.dp)
            .background(Color.Black, CircleShape),
             contentAlignment = Alignment.Center
        ){
            Text(text= user.name.substring(0, 1),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
            )
        }
        Column(modifier = Modifier.padding(start = 6.dp), verticalArrangement = Arrangement.Center) {
            Text(text = user.username,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
            )
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Text(text = user.email,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
            )

        }
    }
}
