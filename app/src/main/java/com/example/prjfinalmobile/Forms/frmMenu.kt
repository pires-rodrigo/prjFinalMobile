package com.example.prjfinalmobile.Forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prjfinalmobile.ClassComponents.MyTopBar
import com.example.prjfinalmobile.DataBase.SystemDataBase
import com.example.prjfinalmobile.Viewmodel.UsuarioViewModel
import com.example.prjfinalmobile.Viewmodel.UsuarioViewModelFatory
import kotlin.system.exitProcess

private fun isSelected(currentDestination: NavDestination?, route:String): Boolean {
    return currentDestination?.hierarchy?.any {it.route == route} == true
}

@Composable
fun frmHome(id: String){
    Scaffold(
        topBar = {
            MyTopBar("Bem Vindo!") { exitProcess(0) }
        }
    ) { it ->

        val db = SystemDataBase.getDataBase(LocalContext.current)

        val usuarioViewModel: UsuarioViewModel = viewModel(
            factory = UsuarioViewModelFatory(db)
        )


        LaunchedEffect(id) {
            if (id.isNotEmpty()) {
                val user = usuarioViewModel.findById(id.toLong())
                user?.let { usuarioViewModel.setUiState(user) }
            }
        }

        val state = usuarioViewModel.usuState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ) {

            Row {
                Text(
                    text = "Bem-vindo: " + state.value.login,
                    fontSize = 34.sp
                )
            }
        }
    }
}

@Composable
fun frmMenu(id: String)
{
    val navController = rememberNavController()

    Scaffold (
        bottomBar = {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.value?.destination

            BottomNavigation {

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "frmHome"),
                    onClick = { navController.navigate("frmHome") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = ""
                        )
                    }
                )

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "frmViagem"),
                    onClick = { navController.navigate("frmViagem") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = ""
                        )
                    }
                )

                BottomNavigationItem(
                    selected = isSelected(currentDestination, "frmSobre"),
                    onClick = { navController.navigate("frmSobre") },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = ""
                        )
                    }
                )
            }
        }
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)

        ){
            NavHost(navController = navController,
                    startDestination = "frmHome"
            ){
                composable("frmHome"){
                    frmHome(id)
                }

                composable("frmViagem"){
                    frmViagem()
                }

                composable("frmSobre"){
                    frmSobre()
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMenu() {
    frmMenu("")
}