package com.example.wallet.ui

import android.os.Bundle
import com.example.wallet.data.db.entities.MoneyItem

interface AddDialogListener {
    fun onAddButtonClicked(item: MoneyItem)

}