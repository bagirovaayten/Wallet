package com.example.wallet.ui

import androidx.lifecycle.ViewModel
import com.example.wallet.data.db.entities.MoneyItem
import com.example.wallet.repostory.WalletRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: WalletRepository) : ViewModel() {

    fun upsert(item: MoneyItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.upsert(item)
    }

    fun delete(item: MoneyItem) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(item)
    }

    //fun getAllMoneyItems() = repository.getAllMoneyItems()

    fun getAllMoneyBetweenDateRange(startDate:String ,endDate:String)=repository.getAllMoneyBetweenDateRange(startDate,endDate)
}
