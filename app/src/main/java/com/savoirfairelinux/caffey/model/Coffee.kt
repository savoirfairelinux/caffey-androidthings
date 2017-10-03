package com.savoirfairelinux.caffey.model

/**
 * Created by lsiret on 17-10-02.
 */

const val SIZE_SMALL = 1
const val SIZE_MEDIUM = 2
const val SIZE_LARGE = 3

const val INTENT_PRICE = "PRICE"
const val INTENT_NAME = "NAME"
const val INTENT_SIZE = "SIZE"

data class Coffee(val name: String = "", val price: Int = 0) {
    var size: Int = 1
}