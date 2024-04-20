package com.example.agendadecontatoskotlin.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agendadecontatoskotlin.AppDataBase
import com.example.agendadecontatoskotlin.AtualizarUsuario
import com.example.agendadecontatoskotlin.Dao.UsuarioDao
import com.example.agendadecontatoskotlin.Model.Usuario
import com.example.agendadecontatoskotlin.databinding.ContatoItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContatoAdapter(private val context: Context, private val listaUsuario: MutableList<Usuario>):
    RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        val itemLista = ContatoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ContatoViewHolder(itemLista)
    }


    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        holder.txtNome.text = listaUsuario[position].nome
        holder.txtSobrenome.text = listaUsuario[position].sobrenome
        holder.txtIdade.text = listaUsuario[position].idade
        holder.txtTelefone.text = listaUsuario[position].celular

        holder.btEditar.setOnClickListener{
            val intent = Intent(context, AtualizarUsuario::class.java)
            intent.putExtra("nome", listaUsuario[position].nome)
            intent.putExtra("sobrenome", listaUsuario[position].sobrenome)
            intent.putExtra("idade", listaUsuario[position].idade)
            intent.putExtra("celular", listaUsuario[position].celular)
            intent.putExtra("uid", listaUsuario[position].uid)
            context.startActivity(intent)
        }

        holder.btLixeira.setOnClickListener{

            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Excluir Contato")
            dialog.setMessage("Você realmente quer excluir esse contato?")

            dialog.setPositiveButton("Sim") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    val usuario = listaUsuario[position]
                    val usuarioDao: UsuarioDao = AppDataBase.getInstance(context).usuarioDao()
                    usuarioDao.deletarUsuario(usuario.uid)
                    listaUsuario.remove(usuario)

                    withContext(Dispatchers.Main){
                        notifyDataSetChanged()
                    }
                }
            }

            dialog.setNegativeButton("Não") { _, _ ->

            }

            dialog.create().show()

        }
    }


    override fun getItemCount() = listaUsuario.size


    inner class ContatoViewHolder(binding: ContatoItemBinding) : RecyclerView.ViewHolder(binding.root) {
            val txtNome = binding.textNome
            val txtSobrenome = binding.textSobrenome
            val txtIdade = binding.textIdade
            val txtTelefone = binding.textCelular
            val btEditar = binding.imgEditar
            val btLixeira = binding.imgLixeira
    }
}