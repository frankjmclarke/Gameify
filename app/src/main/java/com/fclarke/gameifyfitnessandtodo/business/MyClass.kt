package com.fclarke.gameifyfitnessandtodo.business

import com.fclarke.gameifyfitnessandtodo.local.Shared

class MyClass(shared: Shared) {
    enum class CLASS {
        WARRIOR,
        MAGE,
        HEALER,
        ROGUE,
        NONE
    }

    val prefKey = "MY_CLASS"
    var myClass: CLASS = CLASS.WARRIOR

    //lateinit var myClass:CLASS
    init {
        read(shared)
    }

    fun read(sharedPreferences: Shared) {
        var cls: Int? = sharedPreferences?.getInt(prefKey)
        myClass = when (cls) {
            1 -> CLASS.WARRIOR
            2 -> CLASS.MAGE
            3 -> CLASS.HEALER
            4 -> CLASS.ROGUE
            else -> CLASS.NONE
        }
    }

    fun toString(cls:Int) :String{
        return when (cls) {
            1 -> "Warrior"
            2 -> "Mage"
            3 -> "Healer"
            4 -> "Rogue"
            else -> "None"
        }
    }

    fun write(sharedPreferences: Shared) {
        when (myClass) {
            CLASS.WARRIOR -> sharedPreferences?.put(prefKey, 1)
            CLASS.MAGE -> sharedPreferences?.put(prefKey, 2)
            CLASS.HEALER -> sharedPreferences?.put(prefKey, 3)
            CLASS.ROGUE -> sharedPreferences?.put(prefKey, 4)
            else -> sharedPreferences?.put(prefKey, 5)
        }
    }
}