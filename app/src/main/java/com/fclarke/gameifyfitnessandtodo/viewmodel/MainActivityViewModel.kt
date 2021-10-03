package com.fclarke.gameifyfitnessandtodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fclarke.gameifyfitnessandtodo.business.Level
import com.fclarke.gameifyfitnessandtodo.network.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/*
ViewModel classes are used to store the data even the configuration changes like rotating screen.
To avoid these issues, it is recommended to store all UI data in the ViewModel instead of an activity.
 */
class MainActivityViewModel : ViewModel() {
    //https://jensklingenberg.de/learn-how-to-use-livedata/
    var list: MutableLiveData<AllCompletedItems> = MutableLiveData()
    var gold = 0
    var goldL: MutableLiveData<Int> = MutableLiveData()
    private var exp = 0
    private var expL: MutableLiveData<Int> = MutableLiveData()
    private var mana = 0
    private var manaL: MutableLiveData<Int> = MutableLiveData()
    private var strength = 0
    private var strengthL: MutableLiveData<Int> = MutableLiveData()
    private var level = 0
    private var levelL: MutableLiveData<Int> = MutableLiveData()
    private var classNum = 0
    private var classNumL: MutableLiveData<Int> = MutableLiveData()
    private var demonNum = 0
    private var demonNumL: MutableLiveData<Int> = MutableLiveData()

    fun getListObserver(): MutableLiveData<AllCompletedItems> {
        return list //loadApiData() updates this and observes notifyDataSetChanged
    }

    fun addGold(num: Int) {
        gold += num
        goldL.value = gold
    }

    fun getGold(): LiveData<Int?>? {
        if (goldL == null) {
            goldL = MutableLiveData<Int>()
        }
        return goldL
    }

    fun setExp(num: Int) {
        val level = Level()
        var l1 = level.exp2Level(exp)
        exp = num
        expL.value = exp
        var l2 = level.exp2Level(exp)
        if (l2 > l1) {
            setLevel(l2)
        }
 
    }

    private fun setLevel(num: Int) {//level is a calculated field
        level = num
        levelL.value = level
    }

    fun recalcLevel(): Int {
        val level = Level()
        return level.exp2Level(exp)
    }

    fun getExp(): LiveData<Int?>? {
        if (expL == null) {
            expL = MutableLiveData<Int>()
        }
        return expL
    }

    fun setStrength(num: Int) {
        strength = num
        strengthL.value = strength
    }

    fun getStrength(): LiveData<Int?>? {
        if (strengthL == null) {
            strengthL = MutableLiveData<Int>()
        }
        return strengthL
    }

    fun setMana(num: Int) {
        mana = num
        manaL.value = mana
    }

    fun getMana(): LiveData<Int?>? {
        if (manaL == null) {
            manaL = MutableLiveData<Int>()
        }
        return manaL
    }

    fun setClassNum(num: Int) {
        classNum = num
        classNumL.value = classNum
    }

    fun getClassNum(): LiveData<Int?>? {
        if (classNumL == null) {
            classNumL = MutableLiveData<Int>()
        }
        return classNumL
    }
    fun setDemonNum(num: Int) {
        demonNum = num
        demonNumL.value = demonNum
    }

    fun getDemonNum(): LiveData<Int?>? {
        if (demonNumL == null) {
            demonNumL = MutableLiveData<Int>()
        }
        return demonNumL
    }
    fun getGoldObserver(): MutableLiveData<Int> {
        return goldL //loadApiData() updates this and observes notifyDataSetChanged
    }

    fun makeApiCall(todoistAuth: String, dateTimeString: String) {
        // val dateTimeString ="2021-09-29T20:16:09Z"
        val retroInstance = RetroInstance.getRetroInstance().create(TodoistService::class.java)
        retroInstance.getChanged(todoistAuth, 30, dateTimeString)//
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getListObserverRx())

    }

    private fun getListObserverRx(): Observer<AllCompletedItems> {
        return object : Observer<AllCompletedItems> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                list.postValue(null)
            }

            override fun onNext(t: AllCompletedItems) {
                list.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }
}