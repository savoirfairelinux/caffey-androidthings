package com.savoirfairelinux.caffey

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.savoirfairelinux.caffey.model.Coffee
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageButton.setOnClickListener {
            // Handler code here.
           Log.d("Activiy", "button1")// add custom flags
            startActivity(UserDetailIntent(Coffee("Coffee1" , price = 10)))
        }
        imageButton2.setOnClickListener {
            // Handler code here.
            Log.d("Activiy", "button2")
            startActivity(UserDetailIntent(Coffee("Coffee2" , price = 15)))
        }
    }
}
