package com.fclarke.gameifyfitnessandtodo.business.attributes

import com.fclarke.gameifyfitnessandtodo.local.Shared

class PlayerClass(shared: Shared) : Attribute(shared, prefKey = "PLAYER_CLASS") {
    fun getName() :String{
        return when (value) {
            1 -> "Warrior"
            2 -> "Mage"
            3 -> "Healer"
            4 -> "Rogue"
            else -> "None"
        }
    }
}