package com.savoirfairelinux.caffey


import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.savoirfairelinux.caffey.model.Coffee
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {

    private val TAG = MainActivity::class.java.simpleName

    var coffee1: Coffee? = null
    var coffee2: Coffee? = null
    var coffee3: Coffee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("coffee1")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                coffee1 = dataSnapshot.getValue(Coffee::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val myRef2 = database.getReference("coffee2")

        myRef2.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                coffee2 = dataSnapshot.getValue(Coffee::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        val myRef3 = database.getReference("coffee3")

        myRef3.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                coffee3 = dataSnapshot.getValue(Coffee::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        view_1.setOnClickListener {
            Log.d("Activiy", "button1")// add custom flags
            startActivity(UserDetailIntent(coffee1!!))
        }
        view_2.setOnClickListener {
            Log.d("Activiy", "button2")
            startActivity(UserDetailIntent(coffee2!!))
        }
        view_3.setOnClickListener {
            Log.d("Activiy", "button2")
            startActivity(UserDetailIntent(coffee3!!))
        }
    }
}
