package com.example.agendadecontatoskotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.agendadecontatoskotlin.Dao.UsuarioDao
import com.example.agendadecontatoskotlin.Model.Usuario

@Database(entities = [Usuario::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object{

        private const val DATABASE_NOME = "DB_USUARIOS"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDataBase::class.java, DATABASE_NOME
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }

}