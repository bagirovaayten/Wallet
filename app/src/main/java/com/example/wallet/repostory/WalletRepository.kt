package com.example.wallet.repostory

import com.example.wallet.data.db.WalletDataBase
import com.example.wallet.data.db.entities.MoneyItem

class WalletRepository (private val db: WalletDataBase){

    suspend fun upsert(item: MoneyItem) = db.getWalletDao().upsert(item)
    suspend fun delete(item: MoneyItem) = db.getWalletDao().delete(item)
    fun getAllMoneyItems() = db.getWalletDao().getAllMoneyItems()
    fun getAllMoneyBetweenDateRange (startDate:String ,endDate:String)=db.getWalletDao().getAllMoneyBetweenDateRange(startDate,endDate)
}