package com.example.conectarapiraca.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Estabelecimento::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun estabelecimentoDao(): EstabelecimentoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "conectar_arapiraca_db"
                )
                    .addCallback(seedCallback)
                    .build()
                    .also { INSTANCE = it }
            }
        }

        // Usa o "db" recebido pelo próprio callback, sem depender de INSTANCE.
        private val seedCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val sql = "INSERT INTO estabelecimentos " +
                        "(nome, categoria, endereco, cep, telefone, avaliacao) VALUES "
                db.execSQL(sql + "('Restaurante Sabor Nordestino', 'Restaurantes', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0001', 4.5)")
                db.execSQL(sql + "('Restaurante Mesa Farta', 'Restaurantes', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0002', 4.2)")
                db.execSQL(sql + "('Academia Movimento', 'Academias', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0003', 4.7)")
                db.execSQL(sql + "('Academia Força Total', 'Academias', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0004', 4.3)")
                db.execSQL(sql + "('Loja Central', 'Lojas', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0005', 4.1)")
                db.execSQL(sql + "('Loja Moda Já', 'Lojas', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0006', 4.0)")
                db.execSQL(sql + "('Bar do Centro', 'Bares', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0007', 4.4)")
                db.execSQL(sql + "('Bar da Praça', 'Bares', 'Centro, Arapiraca - AL', '57350000', '(82) 99999-0008', 4.6)")
            }
        }
    }
}