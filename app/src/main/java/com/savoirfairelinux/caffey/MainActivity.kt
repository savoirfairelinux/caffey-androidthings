package com.savoirfairelinux.caffey


import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.savoirfairelinux.caffey.model.Coffee
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        view_1.setOnClickListener {
            // Handler code here.
            Log.d("Activity", "button1")// add custom flags
            startActivity(UserDetailIntent(Coffee(getString(R.string.espresso), price = 10)))
        }
        view_2.setOnClickListener {
            // Handler code here.
            Log.d("Activity", "button2")
            startActivity(UserDetailIntent(Coffee(getString(R.string.americano), price = 15)))
        }
        view_3.setOnClickListener {
            // Handler code here.
            Log.d("Activity", "button3")
            startActivity(UserDetailIntent(Coffee(getString(R.string.cappuccino), price = 20)))
        }
    }
}
