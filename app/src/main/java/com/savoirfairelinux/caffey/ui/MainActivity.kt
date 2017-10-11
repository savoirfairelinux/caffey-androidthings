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
import android.os.Bundle
import android.util.Log
import com.google.android.things.contrib.driver.button.Button
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.savoirfairelinux.caffey.R
import com.savoirfairelinux.caffey.model.COFFEE_1
import com.savoirfairelinux.caffey.model.COFFEE_2
import com.savoirfairelinux.caffey.model.COFFEE_3
import com.savoirfairelinux.caffey.model.Coffee
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : Activity() {

    private val TAG = MainActivity::class.java.simpleName

    var coffee1: Coffee? = null
    var coffee2: Coffee? = null
    var coffee3: Coffee? = null

    private lateinit var buttonA: Button
    private lateinit var buttonB: Button
    private lateinit var buttonC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(COFFEE_1)

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                coffee1 = dataSnapshot.getValue(Coffee::class.java)
                textEspresso.text = coffee1!!.name
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val myRef2 = database.getReference(COFFEE_2)

        myRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                coffee2 = dataSnapshot.getValue(Coffee::class.java)
                textAmericano.text = coffee2!!.name
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val myRef3 = database.getReference(COFFEE_3)

        myRef3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                coffee3 = dataSnapshot.getValue(Coffee::class.java)
                textCappuccino.text = coffee3!!.name
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        view_1.setOnClickListener {
            goToSize(coffee1!!)
        }
        view_2.setOnClickListener {
            goToSize(coffee2!!)
        }
        view_3.setOnClickListener {
            goToSize(coffee3!!)
        }
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

    private fun initButtons() {
        // Initialize buttons
        try {
            buttonA = RainbowHat.openButtonA()
            buttonB = RainbowHat.openButtonB()
            buttonC = RainbowHat.openButtonC()
            buttonA.setOnButtonEventListener { _, _ ->
                goToSize(coffee1!!)
            }
            buttonB.setOnButtonEventListener { _, _ ->
                goToSize(coffee2!!)
            }
            buttonC.setOnButtonEventListener { _, _ ->
                goToSize(coffee3!!)
            }
        } catch (e: IOException) {
            throw RuntimeException("Error initializing Buttons", e)
        }
    }

    private fun goToSize(coffee: Coffee) {
        startActivity(CoffeeDetailsActivity.newIntent(this, coffee))
    }
}
