package com.example.filmsSearch.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class AutoDisposable : DefaultLifecycleObserver {
    //Using CompositeDisposable for cancelling all Observable
    lateinit var compositeDisposable: CompositeDisposable

    override fun onDestroy(owner: LifecycleOwner) {
        compositeDisposable.dispose()
    }

    //Link for LifeCycle component
    fun bindTo(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        compositeDisposable = CompositeDisposable()
    }
    //Method for adding Observable in to CompositeDisposable
    fun add(disposable: Disposable) {
        if (::compositeDisposable.isInitialized) {
            compositeDisposable.add(disposable)
        } else {
            throw NotImplementedError("must bind AutoDisposable to a Lifecycle first")
        }
    }
}
//Extension
fun Disposable.addTo(autoDisposable: AutoDisposable) {
    autoDisposable.add(this)
}