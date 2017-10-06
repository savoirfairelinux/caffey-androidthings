package com.savoirfairelinux.caffey.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.savoirfairelinux.caffey.R
import kotlinx.android.synthetic.main.activity_brewing.*

/**
 * Created by hdesousa on 03/10/17.
 */

class BrewingActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brewing)

        iconBrewing.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}