package com.example.wallet.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.wallet.R
import com.example.wallet.data.db.entities.MoneyItem
import kotlinx.android.synthetic.main.dialog_add_wallet.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class AddWalletDialog(context: Context, var addDialogListener: AddDialogListener) :
    AppCompatDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_wallet)
        tvAdd.setOnClickListener {
            val name = etName.text.toString()
            val d = etDate.text.toString().trim()
            val format = SimpleDateFormat("yyyy-MM-dd")
            var date = Date()
            try {
                date = format.parse(d)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val amount = etAmount.text.toString()

            if (name.isEmpty() || amount.isEmpty() || d.isEmpty()) {
                Toast.makeText(context, "Please  enter all informations!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val item = MoneyItem(
                name,
                format.format(date),
                amount.toDouble(),
                inComeCheck.isChecked
            )
            addDialogListener.onAddButtonClicked(item)
            dismiss()

            tvCancel.setOnClickListener {
                cancel()
            }
        }
        var cal = Calendar.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                etDate.setText(sdf.format(cal.time))

            }

        etDate.setOnClickListener {
            DatePickerDialog(
                context, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
