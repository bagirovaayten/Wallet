package com.example.wallet.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.wallet.R
import com.example.wallet.data.db.entities.MoneyItem
import com.example.wallet.others.ViewPager
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.math.round

class MainFragment : Fragment() {
    lateinit var walletViewModel: WalletViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ViewPager
        val pageerAdapter = ViewPager(childFragmentManager)
        pageerAdapter.addFragment(
            fragment = MoneyInFragment(),
            title = "MONEY IN"
        )
        pageerAdapter.addFragment(
            fragment = MoneyOutFragment(),
            title = "MONEY OUT"
        )
        viewPager.adapter = pageerAdapter
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)!!.text = "MONEY IN"
        tabs.getTabAt(1)!!.text = "MONEY OUT"


        //floatingActionButton
        walletViewModel = (activity as MainActivity).walletViewModel

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
        month.text = SimpleDateFormat("MMMM").format(Date())


        walletViewModel.getAllMoneyBetweenDateRange(currentDate, nextDate)
            .observe(requireActivity(), {
                avaiableMoney.text = ((sumArray(it.filter { p -> p.isIncome })) - (sumArray(it.filter { p -> !p.isIncome }))).toString() + " m"
                moneyInCount.text =
                    "+ " + sumArray(it.filter { p -> p.isIncome })
                        .toString() + "m"
                moneyOutCount.text =
                    "- " + sumArray(it.filter { p -> !p.isIncome })
                        .toString() + "m"
            })


        floatingActionBtn.setOnClickListener {
            AddWalletDialog(requireContext(), object : AddDialogListener {
                override fun onAddButtonClicked(item: MoneyItem) {
                    walletViewModel.upsert(item)
                }

            }).show()
        }

        calendarIcon.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_mainFragment_to_historyFragment)

        }
    }

    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    private fun sumArray(array: List<MoneyItem>): Double{
        var sum: BigDecimal = BigDecimal.ZERO
        for (element in array) {
            sum += BigDecimal.valueOf(element.moneyCount)
        }
        return sum.toDouble()
    }


}
