package com.example.wallet.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet.data.db.entities.MoneyItem


@Database(
    entities = [MoneyItem::class],
    version = 1
)
abstract class WalletDataBase : RoomDatabase() {
    abstract fun getWalletDao(): WalletDao

    companion object {

        @Volatile
        private var instance: WalletDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: creatDatabase(context).also { instance = it }
        }

        private fun creatDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                WalletDataBase::class.java,
                "WalletDB.db"
            ).build()
    }}