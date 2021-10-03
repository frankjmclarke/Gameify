package com.fclarke.gameifyfitnessandtodo.business.characters

import com.fclarke.gameifyfitnessandtodo.business.MyClass
import com.fclarke.gameifyfitnessandtodo.local.Shared

class Demon(shared: Shared)  : Entity(shared) {
    override fun addGold(sum: Int) {
        val bonus: Int = (0..sum).random()
        var sumPlus = sum + when (myClass.myClass) {
            MyClass.CLASS.WARRIOR -> 0
            MyClass.CLASS.MAGE -> 0
            MyClass.CLASS.HEALER -> 0
            MyClass.CLASS.ROGUE -> bonus
            else -> 0
        }
        gold.earnings( sumPlus)
    }
}