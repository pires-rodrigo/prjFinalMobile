package com.example.prjfinalmobile.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prjfinalmobile.ClassDao.UsuarioDao
import com.example.prjfinalmobile.DataBase.SystemDataBase
import com.example.prjfinalmobile.Class.Usuario
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModelFatory(val db: SystemDataBase) : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return UsuarioViewModel(db.usuarioDao) as T
    }
}
class UsuarioViewModel(val usuarioDao:UsuarioDao) : ViewModel() {
    private val _uiState = MutableStateFlow(Usuario())
    val usuState : StateFlow<Usuario> = _uiState.asStateFlow()

    fun updateLogin(newLogin : String){
        _uiState.update { it.copy(login = newLogin) }
    }

    fun updateSenha(newSenha : String){
        _uiState.update { it.copy(senha = newSenha) }
    }

    fun updateEmail (newEmail : String){
        _uiState.update { it.copy(email = newEmail) }
    }

    fun updateId(id: Long){
        _uiState.update {
            it.copy(id = id)
        }
    }

    fun save(){
        viewModelScope.launch {
            val id = usuarioDao.upsert(usuState.value)
            if (id > 0){
                updateId(id)
            }
        }
    }

    suspend fun findById(id: Long): Usuario? {
        val deferred : Deferred<Usuario?> = viewModelScope.async {
            usuarioDao.findById(id)
        }
        return deferred.await()
    }

    suspend fun findByLogin(login: String, senha: String) : Long?{
        val deferred : Deferred<Usuario?> = viewModelScope.async {
            usuarioDao.findByLogin(login)
        }
        val user = deferred.await()

        if (login == user?.login && senha == user.senha){
            return user.id
        }
        else{
            return null
        }
    }

    fun setUiState(usuario: Usuario) {
        _uiState.value = usuState.value.copy(
            id = usuario.id,
            login = usuario.login,
            senha = usuario.senha,
            email = usuario.email
        )
    }
}