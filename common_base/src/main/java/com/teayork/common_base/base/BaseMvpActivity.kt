package com.teayork.common_base.base

import android.os.Bundle
import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.event.EventMap
import com.teayork.common_base.mvp.view.MvpActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseMvpActivity<out P : IPresenterContract> : MvpActivity<P>() {


    var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(getLayoutId())


        init(savedInstanceState)
//        initView()
//        registerEvent()
        initData()
    }

//    open fun isHasBus(): Boolean {
//        return false
//    }
//
//    protected fun registerEvent() {
//        if (isHasBus()) {
//            val disposable = RxBusTools.getDefault().register(EventMap.BaseEvent::class.java, Consumer { onEvent(it) })
//            disposables.add(disposable)
//        }
//    }

//    open fun onEvent(event: EventMap.BaseEvent) {
//    }


    open fun init(savedInstanceState: Bundle?) {}


    abstract fun initData()



    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
//        LoadingUtils.onDestory()
    }


}