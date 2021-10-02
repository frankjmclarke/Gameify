package com.fclarke.gameifyfitnessandtodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        exp = num
        expL.value = exp
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