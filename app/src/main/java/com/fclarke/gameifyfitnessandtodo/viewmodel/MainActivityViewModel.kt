package com.fclarke.gameifyfitnessandtodo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fclarke.gameifyfitnessandtodo.MainActivity
import com.fclarke.gameifyfitnessandtodo.network.ListModel
import com.fclarke.gameifyfitnessandtodo.network.RetroInstance
import com.fclarke.gameifyfitnessandtodo.network.TodoistService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel: ViewModel() {
    var list: MutableLiveData<ListModel> = MutableLiveData()

    fun getListObserver(): MutableLiveData<ListModel> {
        return list
    }

    fun makeApiCall(sync_token: String, resource_types:String) {
        val retroInstance = RetroInstance.getRetroInstance().create(TodoistService::class.java)
        retroInstance.getListFromApi(MainActivity.todoistAuth, sync_token, resource_types)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getListObserverRx())
    }

    private fun getListObserverRx():Observer<ListModel> {
        return object :Observer<ListModel>{
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                list.postValue(null)
            }

            override fun onNext(t: ListModel) {
                list.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }
}