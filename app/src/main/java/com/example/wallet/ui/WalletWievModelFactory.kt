package com.example.wallet.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallet.repostory.WalletRepository

class WalletWievModelFactory (private val repository: WalletRepository): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WalletViewModel(repository) as T
}}