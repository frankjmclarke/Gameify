package com.fclarke.gameifyfitnessandtodo.business.attributes

import com.fclarke.gameifyfitnessandtodo.R
import com.fclarke.gameifyfitnessandtodo.local.Shared

class DemonClass(shared: Shared) : Attribute(shared, prefKey = "DEMON_CLASS") {
    fun getName() :String{
        return when (value) {
            1 -> "Chaos"
            2 -> "Phobos"
            3 -> "Cronos"
            4 -> "Lyssa"//Ares war
            else -> "None"
        }
    }
    fun getImage() :Int{
        return when (value) {
            1 -> R.drawable.sharpsmile
            2 -> R.drawable.checklist
            3 -> R.drawable.sharpsmile
            4 -> R.drawable.sharpsmile
            else -> R.drawable.sharpsmile
        }
    }
}