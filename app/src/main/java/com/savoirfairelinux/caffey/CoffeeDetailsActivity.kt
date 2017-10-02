package com.savoirfairelinux.caffey

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.savoirfairelinux.caffey.model.Coffee
import com.google.android.things.contrib.driver.ht16k33.Ht16k33
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay



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

class CoffeeDetailsActivity : Activity() {

    lateinit var coffee: Coffee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val price = intent.getIntExtra(INTENT_PRICE,0)
        val name = intent.getStringExtra(INTENT_NAME)
        requireNotNull(price) { "no price provided in Intent extras" }
        requireNotNull(name) { "no name provided in Intent extras" }
        coffee = Coffee(name, price)
        displayPrice(coffee.price)
    }

    fun displayPrice(price:Int){

        var diplayablePrice: String

        diplayablePrice = String.format("%2.2f", price / 10.0)

        val segment = RainbowHat.openDisplay()
        segment.setBrightness(Ht16k33.HT16K33_BRIGHTNESS_MAX)
        segment.display(diplayablePrice)
        segment.setEnabled(true)
        // Close the device when done.
        segment.close()
    }

}