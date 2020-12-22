package com.example.wallet.others

import android.graphics.Color
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.data.db.entities.MoneyItem
import com.example.wallet.ui.WalletViewModel
import kotlinx.android.synthetic.main.money_list.view.*


class WalletAdapter(var list: List<MoneyItem>, private val viewModel: WalletViewModel) :
    RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {

    inner class WalletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.money_list, parent, false)
        return WalletViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        //val format = SimpleDateFormat("dd.MM.yyyy")

        holder.itemView.title.text = list[position].title
        holder.itemView.deleteImg.setOnClickListener{
            viewModel.delete(list[position])
        }

        //holder.itemView.date.text = format.format(list[position].date)
        holder.itemView.date.text = list[position].date
        if (list[position].isIncome) {
            holder.itemView.money.text = "+ " + list[position].moneyCount.toString() + " ₼"
            holder.itemView.money.setTextColor(Color.GREEN)
        } else {
            holder.itemView.money.text = "- " + list[position].moneyCount.toString() + " ₼"
            holder.itemView.money.setTextColor(Color.RED)

        }
    }

    fun update(list: List<MoneyItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount():
            Int = list.size




        }


