package com.example.wallet.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wallet.data.db.entities.MoneyItem

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: MoneyItem)

    @Delete()
    suspend fun delete(item: MoneyItem)

    @Query("SELECT*FROM MONEY_ITEM")
    fun getAllMoneyItems(): LiveData<List<MoneyItem>>


    @Query("SELECT*FROM MONEY_ITEM WHERE DATE BETWEEN :startDate AND :endDate")
    fun getAllMoneyBetweenDateRange (startDate:String ,endDate:String): LiveData<List<MoneyItem>>

}