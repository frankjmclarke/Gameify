package com.fclarke.gameifyfitnessandtodo.business

import android.util.Log

class Level {
    var experience = 0
    var level = 0

    fun exp2Level(exp: Int): Int {
        if (exp < 150)
            return exp / 25
        if (exp < 210)
            return 5
        return level6(exp)
    }

    private fun level6(expTarget: Int): Int {
        var high = expTarget / 20
        var low = 6
        var mid = 0
        var expM = 0

        while (low <= high) {//binary search
            mid = low + ((high - low) / 2)
            expM = level2Exp(mid) //instead of an array lookup, we do a level2Exp lookup
            when {
                expTarget > expM -> low = mid + 1 // element is greater than middle element of array, so it will be in right half of array
                expTarget == expM -> return mid // found the element
                expTarget < expM -> high = mid - 1 //element is less than middle element of array, so it will be in left half of the array.
            }
        }
        return high
    }

    fun level2Exp(level: Int): Int { //magic arbitrary formula
        var lev: Float = level * level * 0.25f
        lev += (level * 10) + 139.75f
        lev += 5f//round to nearest 10
        lev /= 10f
        var l: Int = lev.toInt()
        return l * 10
    }

    private fun testPrint(){
        var lev =Level()
        for (i in 10..50)
            Log.i("*** Exp= ",(i*10).toString()+" Level= "+lev.exp2Level(i*10).toString())
    }
}