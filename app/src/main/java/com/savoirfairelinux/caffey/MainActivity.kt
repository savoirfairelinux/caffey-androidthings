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

        imageButton.setOnClickListener {
            // Handler code here.
            Log.d("Activiy", "button1")// add custom flags
            startActivity(UserDetailIntent(Coffee("Coffee1", price = 10)))
        }
        imageButton2.setOnClickListener {
            // Handler code here.
            Log.d("Activiy", "button2")
            startActivity(UserDetailIntent(Coffee("Coffee2", price = 15)))
        }
    }
}
