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
import com.savoirfairelinux.caffey.R
import com.savoirfairelinux.caffey.model.*
import kotlinx.android.synthetic.main.activity_summary.*

class SummaryActivity : Activity() {

    companion object {
        fun newIntent(context: Context, coffee: Coffee): Intent {
            val intent = Intent(context, SummaryActivity::class.java)
            intent.putExtra(INTENT_PRICE, coffee.price)
            intent.putExtra(INTENT_NAME, coffee.name)
            intent.putExtra(INTENT_SIZE, coffee.size)
            intent.putExtra(INTENT_SUGAR, coffee.sugar)
            return intent
        }
    }

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

        backButton.setOnClickListener {
            finish()
        }

        payButton.setOnClickListener {
            startActivity(Intent(this, BrewingActivity::class.java))
        }
    }

    private fun formatPrice(price: Int): String {
        return String.format("$%1.2f", price / 10.0)
    }

    private fun toStringSize(size: Int): CharSequence? {
        when (size) {
            SIZE_LARGE -> return "Large Size"
            SIZE_MEDIUM -> return "Medium Size"
            SIZE_SMALL -> return "Smal Size"
        }
        return "Unexpected Size"
    }
}