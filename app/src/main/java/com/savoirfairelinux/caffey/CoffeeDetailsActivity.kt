package com.savoirfairelinux.caffey

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.savoirfairelinux.caffey.model.*
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.IOException


/**
 * Created by lsiret on 17-10-02.
 */
fun Context.UserDetailIntent(coffee: Coffee): Intent {
    return Intent(this, CoffeeDetailsActivity::class.java).apply {
        putExtra(INTENT_PRICE, coffee.price)
        putExtra(INTENT_NAME, coffee.name)
    }
}

class CoffeeDetailsActivity : Activity() {

    private val TAG = CoffeeDetailsActivity::class.java.simpleName

    lateinit var coffee: Coffee

    private lateinit var buttonA: Button
    private lateinit var buttonB: Button
    private lateinit var buttonC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val price = intent.getIntExtra(INTENT_PRICE, 0)
        val name = intent.getStringExtra(INTENT_NAME)
        requireNotNull(price) { "no price provided in Intent extras" }
        requireNotNull(name) { "no name provided in Intent extras" }
        coffee = Coffee(name, price)

        view_1.setOnClickListener {
            val selected = Coffee(coffee.name, coffee.price + PRICE_SMALL)
            selected.size = SIZE_SMALL
            goToSugar(selected)
        }
        view_2.setOnClickListener {
            val selected = Coffee(coffee.name, coffee.price + PRICE_MEDIUM)
            selected.size = SIZE_MEDIUM
            goToSugar(selected)
        }
        view_3.setOnClickListener {
            val selected = Coffee(coffee.name, coffee.price + PRICE_LARGE)
            selected.size = SIZE_LARGE
            goToSugar(selected)
        }

        backButton.setOnClickListener {
            finish()
        }
        textSmallPrice.text = formatPrice(coffee.price + PRICE_SMALL)
        textMediumPrice.text = formatPrice(coffee.price + PRICE_MEDIUM)
        textLargePrice.text = formatPrice(coffee.price + PRICE_LARGE)
    }

    fun formatPrice(price: Int): String {
        return String.format("$%1.2f", price / 10.0)
    }

    override fun onResume() {
        super.onResume()
        initButtons()
    }

    override fun onPause() {
        super.onPause()
        try {
            buttonA.close()
            buttonB.close()
            buttonC.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing display", e)
        }
    }

    private fun goToSugar(coffee: Coffee) {
        startActivity(SugarLevelIntent(coffee))
    }

    private fun initButtons() {
        // Initialize buttons
        try {
            buttonA = RainbowHat.openButtonA()
            buttonB = RainbowHat.openButtonB()
            buttonC = RainbowHat.openButtonC()
            buttonA.setOnButtonEventListener { _, _ ->
                val selected = Coffee(coffee.name, coffee.price + PRICE_SMALL)
                selected.size = SIZE_SMALL
                goToSugar(selected)
            }
            buttonB.setOnButtonEventListener { _, _ ->
                val selected = Coffee(coffee.name, coffee.price + PRICE_MEDIUM)
                selected.size = SIZE_MEDIUM
                goToSugar(selected)
            }
            buttonC.setOnButtonEventListener { _, _ ->
                val selected = Coffee(coffee.name, coffee.price + PRICE_LARGE)
                selected.size = SIZE_LARGE
                goToSugar(selected)
            }
        } catch (e: IOException) {
            throw RuntimeException("Error initializing Buttons", e)
        }
    }
}