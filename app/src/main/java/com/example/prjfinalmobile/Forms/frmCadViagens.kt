package com.example.prjfinalmobile.Forms

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prjfinalmobile.Class.TipoViagem
import com.example.prjfinalmobile.ClassComponents.MyTopBar
import com.example.prjfinalmobile.DataBase.SystemDataBase
import com.example.prjfinalmobile.Viewmodel.ViagemViewModel
import com.example.prjfinalmobile.Viewmodel.ViagemViewModelFatory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun frmCadViagens(onBack: ()->Unit, viagemId : Long?) {
    Scaffold(
        topBar = {
            MyTopBar("Cadastrar viagem") { onBack() }
        }
    ) { it ->
        val context = LocalContext.current
        val db = SystemDataBase.getDataBase(context)
        val viagemViewModel: ViagemViewModel = viewModel(
            factory = ViagemViewModelFatory(db)
        )
        val state = viagemViewModel.uiState.collectAsState()

        val showDatePickerDialogInicio = remember { mutableStateOf(false) }
        val datePickerStateInicio =
            remember { mutableStateOf(DatePickerState(CalendarLocale("PT-BR"))) }

        val showDatePickerDialogFinal = remember { mutableStateOf(false) }
        val datePickerStateFinal =
            remember { mutableStateOf(DatePickerState(CalendarLocale("PT-BR"))) }

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Destino",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Row {
                OutlinedTextField(
                    value = state.value.destino,
                    onValueChange = { viagemViewModel.updateDestino(it) },
                    modifier = Modifier
                        .weight(4f)
                        .padding(top = 10.dp)
                )
            }

            Row {
                Text(
                    text = "Tipo",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 10.dp)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.value.tipo == TipoViagem.Lazer,
                    onClick = { viagemViewModel.updateTipo(TipoViagem.Lazer) },
                    modifier = Modifier
                        .weight(0.5f)
                )

                Text(
                    text = "Lazer",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.value.tipo == TipoViagem.Negocio,
                    onClick = { viagemViewModel.updateTipo(TipoViagem.Negocio) },
                    modifier = Modifier
                        .weight(0.5f)
                )

                Text(
                    text = "Negócios",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .weight(1.5f)
                )
            }

                Row {

                    Text(
                        text = "Data Inicio",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1.5f)
                            .padding(top = 16.dp)
                    )
                }

                Row {

                    if (showDatePickerDialogInicio.value) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePickerDialogInicio.value = false },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        datePickerStateInicio.value.selectedDateMillis?.let { millis ->
                                            viagemViewModel.updateDtIni(Date(millis))
                                        }
                                        showDatePickerDialogInicio.value = false
                                    }) {
                                    Text(text = "Escolher data")
                                }
                            },
                            modifier = Modifier
                                .weight(4f)
                        ) {
                            DatePicker(state = datePickerStateInicio.value)
                        }
                    }

                    OutlinedTextField(
                        value = state.value.dtIni?.time?.toBrazilianDateFormat() ?: "",
                        onValueChange = { /* Não precisamos atualizar aqui, pois o DatePicker faz isso */ },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) {
                                    showDatePickerDialogInicio.value = true
                                }
                            },
                        readOnly = true
                    )

                }

                Row {

                    Text(
                        text = "Data Final",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1.5f)
                            .padding(top = 16.dp)
                    )
                }

                Row {

                    if (showDatePickerDialogFinal.value) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePickerDialogFinal.value = false },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        datePickerStateFinal.value.selectedDateMillis?.let { millis ->
                                            viagemViewModel.updateDtFim(Date(millis))
                                        }
                                        showDatePickerDialogFinal.value = false
                                    }) {
                                    Text(text = "Escolher data")
                                }
                            },
                            modifier = Modifier
                                .weight(4f)
                        ) {
                            DatePicker(state = datePickerStateFinal.value)
                        }
                    }

                    OutlinedTextField(
                        value = state.value.dtIni?.time?.toBrazilianDateFormat() ?: "",
                        onValueChange = { /* Não precisamos atualizar aqui, pois o DatePicker faz isso */ },
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .onFocusChanged {
                                if (it.isFocused) {
                                    showDatePickerDialogFinal.value = true
                                }
                            },
                        readOnly = true
                    )

                }

                Row {

                    Text(
                        text = "Orçamento",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .weight(1.5f)
                            .padding(top = 16.dp)
                    )
                }

                Row {
                    OutlinedTextField(
                        value = state.value.orcamento?.toString() ?: "",
                        onValueChange = { viagemViewModel.updateOrcamento(it.toFloat()) },
                        modifier = Modifier
                            .weight(4f)
                            .padding(top = 10.dp)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            viagemViewModel.save()
                            Toast.makeText(context, "Viagem salva!", Toast.LENGTH_SHORT).show()
                            onBack()
                        },
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .weight(2f)
                    ) {
                        Text(text = "Salvar")
                    }
                }

            }

        }
    }


    fun Long.toBrazilianDateFormat(
        pattern: String = "dd/MM/yyyy"
    ): String {
        val date = Date(this)
        val formatter = SimpleDateFormat(
            pattern, Locale("pt-br")
        ).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        return formatter.format(date)
    }
