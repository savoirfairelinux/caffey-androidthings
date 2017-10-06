package com.savoirfairelinux.caffey.ui

import android.graphics.Color

/**
 * Created by hdesousa on 02/10/17.
 */
/**
 * Helper methods for computing outputs on the Rainbow HAT
 */

const val MAX_SUGAR = 100

object RainbowUtil {
    /* LED Strip Color Constants*/
    private var sRainbowColors: IntArray = IntArray(7)

    /* Sugar Range */
    private var sugarRanges: IntArray = IntArray(7)

    init {
        for (i in sRainbowColors.indices) {
            val hsv = floatArrayOf(i * 240f / sRainbowColors.size, 1.0f, 1.0f)
            sRainbowColors[i] = Color.HSVToColor(255, hsv)
        }

        var sugarRange = MAX_SUGAR / 7

        for (i in sugarRanges.indices) {
            sugarRanges[i] = sugarRange
            sugarRange += MAX_SUGAR / 7
        }
    }

    /**
     * Return an array of colors for the LED strip based on the given pressure.
     * @param pressure Pressure reading to compare.
     * @return Array of colors to set on the LED strip.
     */
    fun getWeatherStripColors(sugarLevel: Int): IntArray {
        val colors = IntArray(sRainbowColors.size)

        sugarRanges.indices
                .takeWhile { it < sugarLevel }
                .forEach { colors[it] = sRainbowColors.reversedArray()[it] }

        return colors.reversedArray()
    }
}