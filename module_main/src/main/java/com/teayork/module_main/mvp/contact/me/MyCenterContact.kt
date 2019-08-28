package com.teayork.module_main.mvp.contact.me

import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract

/**
 * author：pengzhixian on 2019-08-08 16:10
 * e-mail：759560522@qq.com
 */
interface MyCenterContact {
    interface IView : IBaseViewContract {
        fun setData(list:ArrayList<String>)
        fun setFail()
//        fun collectResult(id: Int)
//        fun cancelCollectResult(id: Int)
    }

    interface IPresenter : IPresenterContract {
        fun getData(page: Int)
//        fun collectHttp(id: Int)
//        fun cancelCollectHttp(id: Int)
    }

}