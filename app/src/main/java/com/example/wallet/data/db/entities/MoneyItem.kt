package com.example.wallet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "money_item")
data class MoneyItem(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "money_count")
    var moneyCount: Double,
    @ColumnInfo(name = "is_income")
    var isIncome: Boolean

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null


    override fun toString(): String {
        return "MoneyItem(title='$title', date='$date', moneyCount=$moneyCount, isIncome=$isIncome, id=$id)"
    }
}
