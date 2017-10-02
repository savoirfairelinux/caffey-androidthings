package com.savoirfairelinux.caffey

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.savoirfairelinux.caffey.model.Coffee

/**
 * Created by lsiret on 17-10-02.
 */
fun Context.UserDetailIntent(coffee: Coffee): Intent {
    return Intent(this, CoffeeDetailsActivity::class.java).apply {
        putExtra(INTENT_PRICE, coffee.price)
    }
}
private const val INTENT_PRICE = "PRICE"

class CoffeeDetailsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val userId = intent.getStringExtra(INTENT_PRICE)
        requireNotNull(userId) { "no price provided in Intent extras" }
    }
}