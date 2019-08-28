package com.teayork.common_base.mvp.view

import android.os.Bundle
import com.teayork.common_base.base.BaseFragment
import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract

/**
 * Created by huanghaijie on 2018/5/16.
 */
abstract class MvpFragment<out T : IPresenterContract> : BaseFragment(), IBaseView<T>, IBaseViewContract {

    private val mPresenter: T by lazy {
        val clazz = registerPresenter()
        val constructor = clazz.getConstructor()
        val presenter = constructor.newInstance()
        presenter.registerMvpView(this)
        presenter
    }

    fun getPresenter() = mPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mPresenter)
    }
}