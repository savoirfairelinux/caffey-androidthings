package com.savoirfairelinux.caffey.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.savoirfairelinux.caffey.R
import com.savoirfairelinux.caffey.model.*
import kotlinx.android.synthetic.main.activity_sugar_level.*
import java.io.IOException

/**
 * Created by hdesousa on 03/10/17.
 */

/**
 * Created by lsiret on 17-10-02.
 */

fun Context.SugarLevelIntent(coffee: Coffee): Intent {
    return Intent(this, SugarLevelActivity::class.java).apply {
        putExtra(INTENT_PRICE, coffee.price)
        putExtra(INTENT_NAME, coffee.name)
        putExtra(INTENT_SIZE, coffee.size)
    }
}

class SugarLevelActivity : Activity() {

    private val TAG = CoffeeDetailsActivity::class.java.simpleName

    // Default LED brightness
    private val LEDSTRIP_BRIGHTNESS = 1

    lateinit var coffee: Coffee

    var sugarLevel: Int = 2

    private lateinit var mDisplay: AlphanumericDisplay
    private lateinit var mLedstrip: Apa102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sugar_level)
        val price = intent.getIntExtra(INTENT_PRICE, 0)
        val name = intent.getStringExtra(INTENT_NAME)
        val size = intent.getIntExtra(INTENT_SIZE, SIZE_SMALL)
        requireNotNull(price) { "no price provided in Intent extras" }
        requireNotNull(name) { "no name provided in Intent extras" }
        coffee = Coffee(name, price)
        coffee.size = size

        sugarLevelText.text = sugarLevel.toString()

        backButton.setOnClickListener {
            finish()
        }

        minusButton.setOnClickListener {
            if (sugarLevel > 0) {
                sugarLevel--
                sugarLevelText.text = sugarLevel.toString()
                try {
                    val colors = RainbowUtil.getWeatherStripColors(sugarLevel)
                    mLedstrip.write(colors)
                } catch (e: IOException) {
                    Log.e(TAG, "Error updating display", e)
                }
            }
        }

        addButton.setOnClickListener {
            if (sugarLevel < 7) {
                sugarLevel++
                sugarLevelText.text = sugarLevel.toString()
                try {
                    val colors = RainbowUtil.getWeatherStripColors(sugarLevel)
                    mLedstrip.write(colors)
                } catch (e: IOException) {
                    Log.e(TAG, "Error updating display", e)
                }
            }
        }

        nextButton.setOnClickListener {
            coffee.sugar = sugarLevel
            startActivity(SummaryActivityIntent(coffee))
        }
    }

    override fun onResume() {
        super.onResume()
        initSegmentDisplay()
        initLedStrips()

        displayPrice(coffee.price)
    }

    private fun initSegmentDisplay() {
        // Initialize 7-segment display
        try {
            mDisplay = RainbowHat.openDisplay()
            mDisplay.setEnabled(true)
            displayPrice(coffee.price)
            Log.d(TAG, "Initialized I2C Display")
        } catch (e: IOException) {
            throw RuntimeException("Error initializing display", e)
        }
    }

    private fun initLedStrips() {
        // Initialize LED strip
        try {
            mLedstrip = RainbowHat.openLedStrip()
            mLedstrip.brightness = LEDSTRIP_BRIGHTNESS

            val colors = RainbowUtil.getWeatherStripColors(sugarLevel)
            mLedstrip.write(colors)
            // Because of a known APA102 issue, write the initial value twice.
            mLedstrip.write(colors)

            Log.d(TAG, "Initialized SPI LED strip")
        } catch (e: IOException) {
            throw RuntimeException("Error initializing LED strip", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mDisplay.clear()
            mDisplay.setEnabled(false)
            mDisplay.close()

            mLedstrip.brightness = 0
            mLedstrip.write(IntArray(7))
            mLedstrip.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing display", e)
        }
    }

    private fun displayPrice(price: Int) {
        val displayablePrice: String = String.format("%2.2f", price / 10.0)

        mDisplay.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        mDisplay.display(displayablePrice)
        mDisplay.setEnabled(true)
    }
}