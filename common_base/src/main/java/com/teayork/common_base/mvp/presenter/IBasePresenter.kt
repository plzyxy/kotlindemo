package com.teayork.common_base.mvp.presenter

import com.teayork.common_base.mvp.contract.IBaseViewContract


/**
 * Created by huanghaijie on 2018/5/16.
 */
interface IBasePresenter<out V : IBaseViewContract> {

    fun getMvpView(): V
}