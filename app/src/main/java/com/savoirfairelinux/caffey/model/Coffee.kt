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
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package com.savoirfairelinux.caffey.model

const val SIZE_SMALL = 1
const val SIZE_MEDIUM = 2
const val SIZE_LARGE = 3

const val PRICE_SMALL = 0
const val PRICE_MEDIUM = 2
const val PRICE_LARGE = 3

const val INTENT_PRICE = "PRICE"
const val INTENT_NAME = "NAME"
const val INTENT_SIZE = "SIZE"
const val INTENT_SUGAR = "SUGAR"

data class Coffee(val name: String = "", val price: Int = 0) {
    var size: Int = 1
    var sugar: Int = 1
}