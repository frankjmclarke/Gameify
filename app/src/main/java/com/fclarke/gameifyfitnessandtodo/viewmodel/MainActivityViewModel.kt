package com.fclarke.gameifyfitnessandtodo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fclarke.gameifyfitnessandtodo.MainActivity
import com.fclarke.gameifyfitnessandtodo.network.AllProjects
import com.fclarke.gameifyfitnessandtodo.network.RetroInstance
import com.fclarke.gameifyfitnessandtodo.network.TodoistService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel: ViewModel() {
    var list: MutableLiveData<AllProjects> = MutableLiveData()

    fun getListObserver(): MutableLiveData<AllProjects> {
        return list
    }

    fun makeApiCall(sync_token: String, resource_types:String) {
        val retroInstance = RetroInstance.getRetroInstance().create(TodoistService::class.java)
        retroInstance.getAllProjects(MainActivity.todoistAuth, sync_token, resource_types)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getListObserverRx())
    }

    private fun getListObserverRx():Observer<AllProjects> {
        return object :Observer<AllProjects>{
            override fun onComplete() {
                //hide progress indicator .
            }

            override fun onError(e: Throwable) {
                list.postValue(null)
            }

            override fun onNext(t: AllProjects) {
                list.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
            }
        }
    }
}