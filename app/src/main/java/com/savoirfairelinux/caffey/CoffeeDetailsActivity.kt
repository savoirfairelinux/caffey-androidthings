package com.savoirfairelinux.caffey

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.savoirfairelinux.caffey.model.Coffee
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.IOException
import java.util.*


/**
 * Created by lsiret on 17-10-02.
 */
fun Context.UserDetailIntent(coffee: Coffee): Intent {
    return Intent(this, CoffeeDetailsActivity::class.java).apply {
        putExtra(INTENT_PRICE, coffee.price)
        putExtra(INTENT_NAME, coffee.name)
    }
}

private const val INTENT_PRICE = "PRICE"
private const val INTENT_NAME = "NAME"

class CoffeeDetailsActivity : Activity(), SeekBar.OnSeekBarChangeListener {

    private val TAG = CoffeeDetailsActivity::class.java.simpleName

    // Default LED brightness
    private val LEDSTRIP_BRIGHTNESS = 1

    lateinit var coffee: Coffee

    private lateinit var mDisplay: AlphanumericDisplay
    private lateinit var mLedstrip: Apa102

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

        initSegmentDisplay()
        initLedStrips()
        initButtons()

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
            val colors = IntArray(7)
            Arrays.fill(colors, Color.RED)
            mLedstrip.write(colors)
            // Because of a known APA102 issue, write the initial value twice.
            mLedstrip.write(colors)

            Log.d(TAG, "Initialized SPI LED strip")
        } catch (e: IOException) {
            throw RuntimeException("Error initializing LED strip", e)
        }
    }

    private fun initButtons() {
        // Initialize buttons
        try {
            buttonA = RainbowHat.openButtonA()
            buttonB = RainbowHat.openButtonB()
            buttonC = RainbowHat.openButtonC()
            buttonA.setOnButtonEventListener { _, _ ->
                displayPrice(coffee.price)
            }
            buttonB.setOnButtonEventListener { _, _ ->
                displayPrice(coffee.price + 2)
            }
            buttonC.setOnButtonEventListener { _, _ ->
                displayPrice(coffee.price + 4)
            }
        } catch (e: IOException) {
            throw RuntimeException("Error initializing Buttons", e)
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

            buttonA.close()
            buttonB.close()
            buttonC.close()
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

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        try {
            val colors = RainbowUtil.getWeatherStripColors(progress)
            mLedstrip.write(colors)
        } catch (e: IOException) {
            Log.e(TAG, "Error updating display", e)
        }
    }
}