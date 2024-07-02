package com.example.prjfinalmobile.Forms

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prjfinalmobile.Class.TipoViagem
import com.example.prjfinalmobile.Class.Viagem
import com.example.prjfinalmobile.R
import java.util.Date

fun frmViagemList(){

}

@Composable
fun frmViagem(){
    val list = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        listOf(
            Viagem(1, "África", TipoViagem.Lazer, Date(20240503), Date(20240503), 2000f),
            Viagem(1, "EUA", TipoViagem.Negocio, Date(20240503),Date(20240603), 3500f),
            Viagem(1, "Canada", TipoViagem.Negocio, Date(20240503),Date(20240703), 3500f),
            Viagem(1, "França", TipoViagem.Lazer,  Date(20240503),Date(20240803) , 2000f),
        )
    } else {
        TODO("VERSION.SDK_INT < O")
    }

    val navController = rememberNavController()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("cadastroViagens")
            }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = null)
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = "dest"
            ) {

                composable("frmcadastroViagens") {
                    frmCadViagens(
                        onBack = {navController.navigateUp()}
                    )
                }
                composable("frmViagemList") {
                    frmViagemList()
                }
            }
            LazyColumn {
                items(items = list){
                    ViagemCard(it)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViagemCard(v: Viagem){
    val ctx = LocalContext.current
    Card(elevation = CardDefaults.cardElevation(
        defaultElevation = 6.dp
    ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    Toast
                        .makeText(
                            ctx,
                            "Destino: ${v.destino}",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                },
                onLongClick = {
                    Toast
                        .makeText(
                            ctx,
                            "Destino: ${v.destino}, ${v.dtIni} - ${v.dtFim}",
                            Toast.LENGTH_LONG
                        )
                        .show()
                }
            )
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            if (v.tipo == TipoViagem.Negocio){
                Image(
                    painter = painterResource(id = R.drawable.visiblee),
                    contentDescription = "Negócio",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .padding(top = 0.dp, start = 15.dp)
                )
            }else{
                Image(
                    painter = painterResource(id = R.drawable.nonvisible),
                    contentDescription = "Lazer",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .padding(top = 0.dp, start = 15.dp)
                )
            }

            Column {
                Text(text = "Destino: ${v.destino}", fontSize = 25.sp, style = MaterialTheme.typography.titleLarge)
                Row{
                    Text(text = "${v.dtIni} - ${v.dtFim}")
                }
                Row{
                    Text(text = "Orçamento: R$ ${v.orcamento}")
                }
            }
        }
    }
}