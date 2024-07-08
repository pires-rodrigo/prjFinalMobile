package com.example.prjfinalmobile.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prjfinalmobile.Class.TipoViagem
import com.example.prjfinalmobile.Class.Viagem
import com.example.prjfinalmobile.ClassDao.ViagemDao
import com.example.prjfinalmobile.DataBase.SystemDataBase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class ViagemViewModelFatory(val db: SystemDataBase) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViagemViewModel(db.viagemDao) as T
    }
}

class ViagemViewModel(val viagemDao: ViagemDao): ViewModel() {
    private val _uiState = MutableStateFlow(Viagem())
    val uiState: StateFlow<Viagem> = _uiState.asStateFlow()

    fun updateDestino(destino: String){
        _uiState.update {
            it.copy(destino = destino)
        }
    }

    fun updateTipo(tipo: TipoViagem){
        _uiState.update {
            it.copy(tipo = tipo)
        }
    }

    fun updateDtIni(dtini: Date?){
        _uiState.update {
            it.copy(dtIni = dtini)
        }
    }

    fun updateDtFim(dtfim: Date?){
        _uiState.update {
            it.copy(dtFim = dtfim)
        }
    }

    fun updateOrcamento(orcamento: Float){
        _uiState.update {
            it.copy(orcamento = orcamento)
        }
    }

    fun updateId(id: Long){
        _uiState.update {
            it.copy(id = id)
        }
    }

    fun save(){
        viewModelScope.launch {
            val id = viagemDao.upsert(uiState.value)
            if (id > 0){
                updateId(id)
            }
        }
    }

    fun delete(viagem: Viagem){
        viewModelScope.launch {
            viagemDao.delete(viagem)
        }
    }

    suspend fun findById(id: Long): Viagem? {
        val deferred : Deferred<Viagem?> = viewModelScope.async {
            viagemDao.findByid(id)
        }
        return deferred.await()
    }

    fun setUiState(viagem: Viagem) {
        _uiState.value = uiState.value.copy(
            id = viagem.id,
            destino = viagem.destino,
            tipo = viagem.tipo,
            dtIni = viagem.dtIni,
            dtFim = viagem.dtFim,
            orcamento = viagem.orcamento
        )
    }
}