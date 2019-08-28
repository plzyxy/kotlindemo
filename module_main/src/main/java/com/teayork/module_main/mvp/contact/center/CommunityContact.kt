package com.teayork.module_main.mvp.contact.center

import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract
import com.teayork.module_main.mvp.model.Moment
import java.util.ArrayList

/**
 * author：pengzhixian on 2019-08-15 11:46
 * e-mail：759560522@qq.com
 */
interface CommunityContact {

    interface IView : IBaseViewContract {
        fun setData(list: ArrayList<Moment>)
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