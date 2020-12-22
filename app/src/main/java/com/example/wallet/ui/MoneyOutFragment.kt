package com.example.wallet.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallet.R
import com.example.wallet.data.db.entities.MoneyItem
import com.example.wallet.others.WalletAdapter
import kotlinx.android.synthetic.main.fragment_money_in.*
import kotlinx.android.synthetic.main.fragment_money_out.*
import java.text.SimpleDateFormat
import java.util.*

class MoneyOutFragment : Fragment() {

    private lateinit var viewModel: WalletViewModel
    lateinit var list: List<MoneyItem>
    lateinit var adapters: WalletAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = listOf()
        viewModel = (activity as MainActivity).walletViewModel
        adapters = WalletAdapter(list,viewModel)
        val yearFormat = SimpleDateFormat("yyyy")
        val monthFormat = SimpleDateFormat("MM")

        val currentYear = yearFormat.format(Date())
        val currentMonth = monthFormat.format(Date())

        var nextYear = yearFormat.format(Date())
        var nextMonth = monthFormat.format(Date())

        if (currentMonth == "12") {
            nextYear = (nextYear.toInt() + 1).toString()
            nextMonth = "01"
        }else{
            nextMonth = (currentMonth.toInt() + 1).toString()
        }

        val currentDate: String =
            "$currentYear-$currentMonth-01"
        val nextDate: String = "$nextYear-$nextMonth-01"
        Log.d("fksjfsdf",currentDate)
        Log.d("fksjfsdf",nextDate)

        viewModel.getAllMoneyBetweenDateRange(currentDate,nextDate).observe(viewLifecycleOwner,{
            list = it.filter { s -> !s.isIncome }
            adapters.update(list)
        })


        moneyOutRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapters

        }
    }

}
