package com.example.wallet.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallet.R
import com.example.wallet.data.db.entities.MoneyItem
import com.example.wallet.others.WalletAdapter
import kotlinx.android.synthetic.main.dialog_add_wallet.*
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.android.synthetic.main.fragment_money_in.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors

class HistoryFragment : Fragment() {
    private lateinit var viewModel: WalletViewModel
    lateinit var list: List<MoneyItem>
    lateinit var historyAdapters: WalletAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = listOf()
        viewModel = (activity as MainActivity).walletViewModel
        historyAdapters = WalletAdapter(list, viewModel)

        toolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireView())
                .popBackStack()
        }
        //Calendar
        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                startDate.text = sdf.format(cal.time)


            }

        startDateLinear.setOnClickListener {
            DatePickerDialog(
                requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        val finishDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                finishDate.text = sdf.format(cal.time)

            }
        finishDateLinear.setOnClickListener {
            DatePickerDialog(
                requireContext(), finishDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
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
        viewModel.getAllMoneyBetweenDateRange(currentDate,nextDate).observe(viewLifecycleOwner, {
            list = it
            historyAdapters.update(list)
        })


        startDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter()
            }

        })

        finishDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter()
            }

        })


        historyRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapters

        }

    }

    private fun filter() {
        if (startDate.text.isNotEmpty() && finishDate.text.isNotEmpty()) {
            viewModel.getAllMoneyBetweenDateRange(
                startDate.text.toString(),
                finishDate.text.toString()
            ).observe(viewLifecycleOwner, {
                list = it
                historyAdapters.update(list)
            })
        }
    }
}