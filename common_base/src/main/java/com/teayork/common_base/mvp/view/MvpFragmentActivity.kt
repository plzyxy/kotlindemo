package com.teayork.common_base.mvp.view

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract

/**
 * Created by huanghaijie on 2018/5/16.
 */
abstract class MvpFragmentActivity<out T : IPresenterContract> : FragmentActivity(), IBaseView<T>, IBaseViewContract {

    private val mPresenter: T by lazy {
        val clazz = registerPresenter()
        val constructor = clazz.getConstructor()
        val presenter = constructor.newInstance()
        presenter.registerMvpView(this)
        presenter
    }

    fun getPresenter(): T = mPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.onCreate(this)
        lifecycle.addObserver(mPresenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy(this)
    }
}