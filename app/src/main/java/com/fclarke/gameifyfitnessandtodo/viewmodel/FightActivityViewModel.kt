package com.fclarke.gameifyfitnessandtodo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fclarke.gameifyfitnessandtodo.business.cards.CardData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/*
ViewModel classes are used to store the data even the configuration changes like rotating screen.
To avoid these issues, it is recommended to store all UI data in the ViewModel instead of an activity.
 */
class FightActivityViewModel : ViewModel() {
    //https://jensklingenberg.de/learn-how-to-use-livedata/
    var list: MutableLiveData<CardData> = MutableLiveData()
    var description = ""
    var descriptionL: MutableLiveData<String> = MutableLiveData()
    private var icon = 0
    private var iconL: MutableLiveData<Int> = MutableLiveData()
    private var icon1 = 0
    private var icon1L: MutableLiveData<Int> = MutableLiveData()
    private var icon2 = 0
    private var icon2L: MutableLiveData<Int> = MutableLiveData()
    private var icon3 = 0
    private var icon3L: MutableLiveData<Int> = MutableLiveData()
    private var icon4 = 0
    private var icon4L: MutableLiveData<Int> = MutableLiveData()


    fun getListObserver(): MutableLiveData<CardData> {
        return list //loadApiData() updates this and observes notifyDataSetChanged
    }

    fun setIcon(num: Int) {
        icon = num
        iconL.value = icon
    }

    fun getIcon(): LiveData<Int?>? {
        if (iconL == null) {
            iconL = MutableLiveData<Int>()
        }
        return iconL
    }
    fun setIcon1(num: Int) {
        icon1 = num
        icon1L.value = icon1
    }

    fun getIcon1(): LiveData<Int?>? {
        if (icon1L == null) {
            icon1L = MutableLiveData<Int>()
        }
        return icon1L
    }
    fun setIcon2(num: Int) {
        icon2 = num
        icon2L.value = icon2
    }

    fun getIcon2(): LiveData<Int?>? {
        if (icon2L == null) {
            icon2L = MutableLiveData<Int>()
        }
        return icon2L
    }

    fun setIcon3(num: Int) {
        icon3 = num
        icon3L.value = icon3
    }

    fun getIcon3(): LiveData<Int?>? {
        if (icon3L == null) {
            icon3L = MutableLiveData<Int>()
        }
        return icon3L
    }
    fun setIcon4(num: Int) {
        icon4 = num
        icon4L.value = icon4
    }

    fun getIcon4(): LiveData<Int?>? {
        if (icon4L == null) {
            icon4L = MutableLiveData<Int>()
        }
        return icon4L
    }
    
    
    fun setTheDescription(num: String) {
        description = num
        descriptionL.value = description
    }

    fun getDescription(): LiveData<String?>? {
        if (descriptionL == null) {
            descriptionL = MutableLiveData<String>()
        }
        return descriptionL
    }

/*
    fun makeApiCall(todoistAuth: String, dateTimeString: String) {
        // val dateTimeString ="2021-09-29T20:16:09Z"
        val retroInstance = RetroInstance.getRetroInstance().create(TodoistService::class.java)
        retroInstance.getChanged(todoistAuth, 30, dateTimeString)//
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getListObserverRx())

    }
*/
    private fun getListObserverRx(): Observer<CardData> {
        return object : Observer<CardData> {
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                list.postValue(null)
            }

            override fun onNext(t: CardData) {
                list.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }
}