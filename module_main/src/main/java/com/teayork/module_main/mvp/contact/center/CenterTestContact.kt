package com.teayork.module_main.mvp.contact.center

import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract
import com.teayork.module_main.bean.GankBean

/**
 * author：pengzhixian on 2019-08-09 10:10
 * e-mail：759560522@qq.com
 */
interface CenterTestContact {

    interface IView : IBaseViewContract {
        fun setData(list: GankBean)
        fun setFail()
//        fun collectResult(id: Int)
//        fun cancelCollectResult(id: Int)
    }

    interface IPresenter : IPresenterContract {
        fun getData(cate:String,page: Int)
//        fun collectHttp(id: Int)
//        fun cancelCollectHttp(id: Int)
    }
}