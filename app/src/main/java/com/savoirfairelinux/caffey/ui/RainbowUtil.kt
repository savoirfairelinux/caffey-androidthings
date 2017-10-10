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

import android.graphics.Color

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