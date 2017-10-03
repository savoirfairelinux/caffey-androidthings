package com.savoirfairelinux.caffey

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.savoirfairelinux.caffey.model.*
import kotlinx.android.synthetic.main.activity_summary.*
import java.io.IOException
import java.util.*

/**
 * Created by lsiret on 17-10-02.
 */

fun Context.SummaryActivityIntent(coffee: Coffee): Intent {
    return Intent(this, SummaryActivity::class.java).apply {
        putExtra(INTENT_PRICE, coffee.price)
        putExtra(INTENT_NAME, coffee.name)
        putExtra(INTENT_SIZE, coffee.size)
        putExtra(INTENT_SUGAR, coffee.sugar)
    }
}

class SummaryActivity : Activity() {

    private val TAG = CoffeeDetailsActivity::class.java.simpleName

    lateinit var coffee: Coffee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)
        val price = intent.getIntExtra(INTENT_PRICE, 0)
        val name = intent.getStringExtra(INTENT_NAME)
        val size = intent.getIntExtra(INTENT_SIZE, SIZE_SMALL)
        val sugar = intent.getIntExtra(INTENT_SUGAR, 1)
        requireNotNull(price) { "no price provided in Intent extras" }
        requireNotNull(name) { "no name provided in Intent extras" }
        coffee = Coffee(name, price)
        coffee.size = size
        coffee.sugar = sugar

        value1.text = coffee.name
        value2.text = toStringSize(coffee.size)
        value3.text = coffee.sugar.toString()
        value4.text = formatPrice(coffee.price)

    }

    private fun formatPrice(price: Int): String {
        return String.format("$%1.2f", price / 10.0)
    }
    private fun toStringSize(size: Int): CharSequence? {
        when (size){
            SIZE_LARGE-> return "Large Size"
            SIZE_MEDIUM-> return "Medium Size"
            SIZE_SMALL-> return "Smal Size"
        }
        return "Unexpected Size"
    }
}