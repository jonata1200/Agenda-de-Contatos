package com.example.agendadecontatoskotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agendadecontatoskotlin.Dao.UsuarioDao
import com.example.agendadecontatoskotlin.Model.Usuario
import com.example.agendadecontatoskotlin.databinding.ActivityCadastrarUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CadastrarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityCadastrarUsuarioBinding
    private lateinit var usuarioDao: UsuarioDao
    private val listaUsuarios: MutableList<Usuario> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCadastrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


            binding.btSalvarNumero.setOnClickListener{

                CoroutineScope(Dispatchers.IO).launch {

                    val nome = binding.editNome.text.toString()
                    val sobrenome = binding.editSobrenome.text.toString()
                    val idade = binding.editIdade.text.toString()
                    val celular = binding.editCelular.text.toString()
                    val mensagem: Boolean

                    if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty()){
                        mensagem = false

                    }else{
                        cadastrar(nome, sobrenome, idade, celular)
                        mensagem = true
                    }

                    withContext(Dispatchers.Main){

                        if (mensagem){
                            Toast.makeText(applicationContext, "Sucesso ao cadastrar o usuário!", Toast.LENGTH_SHORT).show()
                            finish()
                        }else{
                            Toast.makeText(applicationContext, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
        }


        binding.toolbarCadastrarUsuario.setNavigationOnClickListener{
            finish()
        }
    }

    private fun cadastrar(nome: String, sobrenome: String, idade: String, celular: String){

        val usuario = Usuario(nome, sobrenome, idade, celular)
        listaUsuarios.add(usuario)

        usuarioDao = AppDataBase.getInstance(this).usuarioDao()
        usuarioDao.inserirUsuario(listaUsuarios)
    }
}