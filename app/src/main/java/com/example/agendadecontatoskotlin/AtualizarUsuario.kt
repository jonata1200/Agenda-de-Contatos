package com.example.agendadecontatoskotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agendadecontatoskotlin.Dao.UsuarioDao
import com.example.agendadecontatoskotlin.databinding.ActivityAtualizarUsuarioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AtualizarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityAtualizarUsuarioBinding
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAtualizarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nomeRecuperado = intent.extras?.getString("nome")
        val sobrenomeRecuperado = intent.extras?.getString("sobrenome")
        val idadeRecuperado = intent.extras?.getString("idade")
        val celularRecuperado = intent.extras?.getString("celular")
        val uidRecuperado = intent.extras!!.getInt("uid")

        binding.atualizarNome.setText(nomeRecuperado)
        binding.atualizarSobrenome.setText(sobrenomeRecuperado)
        binding.atualizarIdade.setText(idadeRecuperado)
        binding.atualizarCelular.setText(celularRecuperado)


        binding.toolbarAtualizarUsuario.setNavigationOnClickListener{
            finish()
        }


        binding.btAtualizarNumero.setOnClickListener{

            CoroutineScope(Dispatchers.IO).launch{

                val nome = binding.atualizarNome.text.toString()
                val sobrenome = binding.atualizarSobrenome.text.toString()
                val idade = binding.atualizarIdade.text.toString()
                val celular = binding.atualizarCelular.text.toString()
                val mensagem: Boolean

                if (nome.isEmpty() || sobrenome.isEmpty() || idade.isEmpty() || celular.isEmpty()){
                    mensagem = false

                }else{
                    mensagem = true
                    atualizarContato(uidRecuperado, nome, sobrenome, idade, celular)

                }

                withContext(Dispatchers.Main){
                    if (mensagem){
                        Toast.makeText(this@AtualizarUsuario, "Sucesso ao atualizar os dados!", Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        Toast.makeText(this@AtualizarUsuario, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                    }
                }

            }

        }

    }

private fun atualizarContato(uid: Int, nome: String, sobrenome: String, idade: String, celular: String){
    usuarioDao = AppDataBase.getInstance(this).usuarioDao()
    usuarioDao.atualizarUsuario(uid, nome, sobrenome, idade, celular)

    }


}