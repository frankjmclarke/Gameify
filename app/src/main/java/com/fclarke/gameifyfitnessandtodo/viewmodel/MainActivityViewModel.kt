package com.fclarke.gameifyfitnessandtodo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fclarke.gameifyfitnessandtodo.network.*
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class MainActivityViewModel : ViewModel() {
    var list: MutableLiveData<AllCompletedItems> = MutableLiveData()

    fun getListObserver(): MutableLiveData<AllCompletedItems> {
        return list
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