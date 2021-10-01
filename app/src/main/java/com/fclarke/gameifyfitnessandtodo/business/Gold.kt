package com.fclarke.gameifyfitnessandtodo.business

import com.fclarke.gameifyfitnessandtodo.local.Shared
import java.time.LocalDateTime

class Gold {
    fun earnings(sharedPreferences:Shared,  earned:Int){
        var dateTime: String = LocalDateTime.now().toString()
        sharedPreferences?.put("TODOIST_SINCE_DATE", dateTime)
        var gold: Int? =sharedPreferences?.getInt("GOLD_AMOUNT")
        if (gold!= null) {
            gold += earned
            sharedPreferences?.put("GOLD_AMOUNT", gold)
        }
    }
}