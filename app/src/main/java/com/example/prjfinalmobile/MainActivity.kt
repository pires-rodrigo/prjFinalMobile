package com.example.prjfinalmobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prjfinalmobile.Forms.frmCadastrarUsuario
import com.example.prjfinalmobile.Forms.frmLogin
import com.example.prjfinalmobile.Forms.frmMenu
import com.example.prjfinalmobile.ui.theme.PrjFinalMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrjFinalMobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myApp()
                }
            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun myApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "frmLogin"
    ) {
        composable("frmLogin") {
            frmLogin(onCadastrarUsuario = {
                navController.navigate("frmCadastrarUsuario")
            },
                onLogin = {
                    navController.navigate("frmMenu/${it}")
                })
        }

        composable("frmcadastrarUsuario") {
            frmCadastrarUsuario(onBack = { navController.navigateUp() })
        }

        composable("frmMenu/{id}") { backStackEntry ->
            backStackEntry.arguments?.getString("id")?.let {
                frmMenu(it)
            }
        }
    }
}