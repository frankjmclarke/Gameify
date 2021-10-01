package com.fclarke.gameifyfitnessandtodo.business

import com.fclarke.gameifyfitnessandtodo.local.Shared

class Experience {
    fun earnings(sharedPreferences: Shared, earned:Int){
        var exp: Int? =sharedPreferences?.getInt("EXPERIENCE")
        if (exp!= null) {
            exp += earned
            sharedPreferences?.put("EXPERIENCE", exp)
        }
    }

}