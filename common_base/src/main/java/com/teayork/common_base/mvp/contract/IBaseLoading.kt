package com.hankkin.library.mvp.contract

import com.teayork.common_base.mvp.contract.IBaseViewContract

/**
 * Created by huanghaijie on 2018/6/12.
 */
interface IBaseLoading : IBaseViewContract {
    fun showLoading()
    fun hideLoading()
}