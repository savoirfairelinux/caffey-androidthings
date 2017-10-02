package com.savoirfairelinux.caffey

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.google.android.things.contrib.driver.apa102.Apa102
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*

class MainActivity : Activity(), SeekBar.OnSeekBarChangeListener {

    private val TAG = MainActivity::class.java.simpleName

    // Default LED brightness
    private val LEDSTRIP_BRIGHTNESS = 1

    private lateinit var mDisplay: AlphanumericDisplay
    private lateinit var mLedstrip: Apa102

    private lateinit var buttonA: Button
    private lateinit var buttonB: Button
    private lateinit var buttonC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBarSugar.max = MAX_SUGAR
        seekBarSugar.setOnSeekBarChangeListener(this)

        // Initialize 7-segment display
        try {
            mDisplay = RainbowHat.openDisplay()
            mDisplay.setEnabled(true)
            mDisplay.display("1234")
            Log.d(TAG, "Initialized I2C Display")
        } catch (e: IOException) {
            throw RuntimeException("Error initializing display", e)
        }

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

        // Initialize buttons
        try {
            buttonA = RainbowHat.openButtonA()
            buttonB = RainbowHat.openButtonB()
            buttonC = RainbowHat.openButtonC()
            buttonA.setOnButtonEventListener { _, _ ->
                coffeeSize.text = "Petit"
            }
            buttonB.setOnButtonEventListener { _, _ ->
                coffeeSize.text = "Moyen"
            }
            buttonC.setOnButtonEventListener { _, _ ->
                coffeeSize.text = "Grand"
            }
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
        } catch (e: IOException) {
            Log.e(TAG, "Error closing display", e)
        }

        try {
            mLedstrip.brightness = 0
            mLedstrip.write(IntArray(7))
            mLedstrip.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error closing LED strip", e)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar) {

    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        try {
            mDisplay.display(progress.toDouble())
            val colors = RainbowUtil.getWeatherStripColors(progress)
            mLedstrip.write(colors)
        } catch (e: IOException) {
            Log.e(TAG, "Error updating display", e)
        }
    }
}
