package com.example.yoshi_continuada_1_2020_2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun calculatePrice(component: View) {
        if (!validateFields()) {
            return
        }

        val messageExampleBytes: Int = edt_message_example.text.length

        val currentSentMessagesCount: Int = edt_current_sent_messages_count.text.toString().toInt()

        val messagesSentPerDayCount: Int = edt_messages_sent_per_day.text.toString().toInt()

        val oneMessageByteCount = 5000.0

        val messagesToBeCharged: Int = ceil(messageExampleBytes / oneMessageByteCount).toInt()

        val startingPriceInDolars: Double = when {
            currentSentMessagesCount <= 1_000_000_000 -> 1.00
            currentSentMessagesCount <= 5_000_000_000 -> 0.8
            else -> 0.7
        }

        val dolarPrice: Double = edt_dolar_price.text.toString().toDouble()

        val pricePerMessage: Double = (startingPriceInDolars / 1_000_000) * dolarPrice

        val pricePerDay: Double = pricePerMessage * messagesSentPerDayCount * messagesToBeCharged

        val pricePerMonth: Double = pricePerDay * 30

        val pricePerYear: Double = pricePerMonth * 12

        val resultColor: Int = when {
            pricePerDay <= 0.5 -> Color.parseColor("#03fc07")
            pricePerDay <= 1.0 -> Color.parseColor("#f5d800")
            else -> Color.parseColor("#db4500")
        }

        val resultString =
            "Preço por dia: R$" + "%.2f".format(pricePerDay) + ", preço por mês: R$" + "%.2f".format(
                pricePerMonth
            ) + ", preço por ano: R$" + "%.2f".format(pricePerYear)

        txt_result.text = resultString
        txt_result.setTextColor(resultColor)
    }

    fun validateFields(): Boolean {
        val isMessageExampleValid: Boolean =
            edt_message_example.text.isNotEmpty() && edt_message_example.text.length >= 10

        if (!isMessageExampleValid) {
            Toast.makeText(
                this,
                "Por favor digite um exemplo de mensagem com no mínimo 10 caracteres",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        val isCurrentSentMessagesCountValid: Boolean =
            edt_current_sent_messages_count.text.isNotEmpty() && edt_current_sent_messages_count.text.toString()
                .toDouble() > 0

        if (!isCurrentSentMessagesCountValid) {
            Toast.makeText(
                this,
                "Por favor digite uma quantidade de mensagens enviadas maior que 0",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        val isMessagesSentPerDayValid: Boolean =
            edt_messages_sent_per_day.text.isNotEmpty() && edt_messages_sent_per_day.text.toString()
                .toDouble() > 0

        if (!isMessagesSentPerDayValid) {
            Toast.makeText(
                this,
                "Por favor digite uma média de mensagens enviadas por dia maior que 0",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        val isDolarPriceValid: Boolean =
            edt_dolar_price.text.isNotEmpty() && edt_dolar_price.text.toString().toDouble() > 0

        if (!isDolarPriceValid) {
            Toast.makeText(
                this,
                "Por favor digite um valor de cotação do dólar maior que 0",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        return true
    }
}