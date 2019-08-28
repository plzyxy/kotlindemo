package com.teayork.common_base.mvp.view

import com.teayork.common_base.contract.IPresenterContract


/**
 * Created by huanghaijie on 2018/6/8.
 */
interface IBaseView<out P : IPresenterContract>{
    fun registerPresenter(): Class<out  P>
}