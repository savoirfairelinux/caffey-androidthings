/*
 *  Copyright (C) 2017 Savoir-faire Linux Inc.
 *
 *  Author: Loic Siret <loic.siret@savoirfairelinux.com>
 *  Author: Hadrien De Sousa <hadrien.desousa@savoirfairelinux.com>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package com.savoirfairelinux.caffey.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.savoirfairelinux.caffey.R
import com.savoirfairelinux.caffey.model.*
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.IOException

class CoffeeDetailsActivity : Activity() {

    companion object {
        fun newIntent(context: Context, coffee: Coffee): Intent {
            val intent = Intent(context, CoffeeDetailsActivity::class.java)
            intent.putExtra(INTENT_PRICE, coffee.price)
            intent.putExtra(INTENT_NAME, coffee.name)
            return intent
        }
    }

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

    private fun formatPrice(price: Int): String {
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
        startActivity(SugarLevelActivity.newIntent(this, coffee))
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