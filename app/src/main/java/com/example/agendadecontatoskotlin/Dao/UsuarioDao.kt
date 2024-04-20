package com.example.agendadecontatoskotlin.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.agendadecontatoskotlin.Model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    fun inserirUsuario(listaUsuarios: MutableList<Usuario>)

    @Query("SELECT * FROM tabelaUsuarios ORDER BY nome ASC")
    fun recuperarUsuario(): MutableList<Usuario>

    @Query("UPDATE tabelaUsuarios SET nome = :novoNome, sobrenome = :novoSobrenome, " +
            "idade = :novaIdade, celular = :novoCelular WHERE uid = :id")
    fun atualizarUsuario(id: Int, novoNome: String, novoSobrenome: String,
                         novaIdade: String, novoCelular: String)

    @Query("DELETE FROM tabelaUsuarios WHERE uid = :id")
    fun deletarUsuario(id: Int)
}