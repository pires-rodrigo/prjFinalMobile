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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prjfinalmobile.Class.TipoViagem
import com.example.prjfinalmobile.Class.Viagem
import com.example.prjfinalmobile.DataBase.SystemDataBase
import com.example.prjfinalmobile.R
import com.example.prjfinalmobile.Viewmodel.ViagemViewModel
import com.example.prjfinalmobile.Viewmodel.ViagemViewModelFatory
import java.util.Date

// metodo vazio para ser passado no startDestination
fun frmViagemList(){

}

@Composable
fun frmViagem(){
    val context = LocalContext.current
    val db = SystemDataBase.getDataBase(context)
    val viagemViewModel: ViagemViewModel = viewModel(
        factory = ViagemViewModelFatory(db)
    )
    val ListaViagens = viagemViewModel.viagemDao.getAll().collectAsState(initial = emptyList()) // lista de viagens

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryFlow.collectAsState(initial = navController.currentBackStackEntry) // Entrada atual da pilha de navegação
    val showFab = currentBackStackEntry?.destination?.route == "frmViagemList" // Mostrar botão flutuante apenas na tela de lista de viagens
    Scaffold(
        floatingActionButton = {
            if (showFab){
                FloatingActionButton(onClick = {
                    navController.navigate("frmCadViagens/${-1L}") // Navegar para a tela de cadastro de viagens com ID -1
                }) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = null)
                }
            }
        }
    ) {it ->
        Column(modifier = Modifier.padding(it)) {
            NavHost(
                navController = navController,
                startDestination = "frmViagemList"
            ) {
                composable("frmCadViagens/{viagemId}",
                    arguments = listOf(navArgument("viagemId") { type = NavType.LongType; defaultValue = -1L })) { backStackEntry ->
                    val viagemId = backStackEntry.arguments?.getLong("viagemId")
                    frmCadViagens(
                        onBack = {navController.navigateUp()},
                        viagemId = if (viagemId != -1L) viagemId else null // Passar o ID da viagem ou null
                    )
                }
                composable("frmViagemList") {
                    frmViagemList()
                }
            }
            LazyColumn {
                items(items = ListaViagens.value){
                    ViagemCard(it, onDelete = {
                        viagemViewModel.delete(it) // Deletar a viagem
                    },
                        onEdit = {
                            navController.navigate("frmCadViagens/${it.id}") // Navegar para a tela de edição de viagens com o ID da viagem
                        })
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViagemCard(loViagem: Viagem, onDelete: () -> Unit, onEdit: () -> Unit){
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
                    onEdit() //se clicar no card -> edita a viagem
                },
                onLongClick = {
                    onDelete() //se der um clique longo - exclui
                }
            )
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            if (loViagem.tipo == TipoViagem.Negocio){
                Image(
                    painter = painterResource(id = R.drawable.negocio),
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
                    painter = painterResource(id = R.drawable.lazer),
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
                Text(text = "Destino: ${loViagem.destino}", fontSize = 25.sp, style = MaterialTheme.typography.titleLarge)
                Row{
                    Text(text = "${loViagem.dtIni?.time?.toBrazilianDateFormat()} - ${loViagem.dtFim?.time?.toBrazilianDateFormat()}")
                }
                Row{
                    Text(text = "Orçamento: R$ ${loViagem.orcamento}")
                }
            }
        }
    }
}