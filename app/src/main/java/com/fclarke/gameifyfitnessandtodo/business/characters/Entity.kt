package com.fclarke.gameifyfitnessandtodo.business.characters

import com.fclarke.gameifyfitnessandtodo.business.attributes.Experience
import com.fclarke.gameifyfitnessandtodo.business.attributes.Gold
import com.fclarke.gameifyfitnessandtodo.business.MyClass
import com.fclarke.gameifyfitnessandtodo.local.Shared
import java.util.*


open class Entity(shared: Shared) {
    val gold = Gold(shared)
    val exp = Experience(shared)
    val myClass = MyClass(shared)
    val mana = MyClass(shared)
    var _shared = shared

    init {
        myClass.read(shared)
    }

    fun IntRange.random() =
        Random().nextInt((endInclusive + 1) - start) + start

    fun addGold(sum: Int) {
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

    fun addExperience(sum: Int) {
        val bonus: Int = (0..sum).random()
        var sumPlus = sum + when (myClass.myClass) {
            MyClass.CLASS.WARRIOR -> 0
            MyClass.CLASS.MAGE -> bonus
            MyClass.CLASS.HEALER -> 0
            MyClass.CLASS.ROGUE -> 0
            else -> 0
        }
        exp.earnings(sumPlus)
    }

    fun addMana(sum: Int) {
        val bonus: Int = (0..sum).random()
        var sumPlus = sum + when (myClass.myClass) {
            MyClass.CLASS.WARRIOR -> 0
            MyClass.CLASS.MAGE -> bonus
            MyClass.CLASS.HEALER -> bonus
            MyClass.CLASS.ROGUE -> 0
            else -> 0
        }
        exp.earnings(sumPlus)
    }

}