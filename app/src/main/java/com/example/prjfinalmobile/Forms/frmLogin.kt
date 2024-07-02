package com.example.prjfinalmobile.Forms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prjfinalmobile.R
import com.example.prjfinalmobile.Viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch


@Composable
fun frmLogin(onCadastrarUsuario: ()->Unit, onLogin: () ->Unit, usuViewModel: UsuarioViewModel = viewModel()){

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val focus = LocalFocusManager.current


    androidx.compose.material3.Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .padding(it)
        )  {
            val loginState = usuViewModel.uiState.collectAsState()
            val passState = usuViewModel.uiState.collectAsState()
            val visivelState = usuViewModel.uiState.collectAsState()

            Text(
                text = "Usuário",
                fontSize = 22.sp
            )

            OutlinedTextField(value = loginState.value.login,
                              onValueChange = {usuViewModel.updateLogin(it)},
                              label = {
                                  Text(text = "login")
                              },
                              modifier = Modifier
                                  .padding(top = 15.dp)
                                  .fillMaxWidth()
            )

            Text(
                text = "Senha",
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(top = 15.dp)

            )

            OutlinedTextField(
                value = passState.value.senha,
                onValueChange = { usuViewModel.updateSenha(it) },
                label = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation =
                if (visivelState.value.visivel)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                trailingIcon = {
                    IconButton(onClick = {
                        usuViewModel.updadeVisivel(!visivelState.value.visivel)
                    }) {
                        if (visivelState.value.visivel)
                            Icon(
                                painterResource(id = R.drawable.visiblee), ""
                            )
                        else
                            Icon(
                                painterResource(id = R.drawable.nonvisible), ""
                            )
                    }
                },
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    if (passState.value.senha == "admin" && loginState.value.login == "admin")
                        onLogin()
                    else {
                        coroutineScope.launch {
                            focus.clearFocus()
                            snackbarHostState.showSnackbar(
                                message = "Login e/ou senha errados!!",
                                withDismissAction = true
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 22.sp
                )
            }

            Button(
                onClick = { onCadastrarUsuario() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Text(text = "Registrar")
            }


        }

    }

}