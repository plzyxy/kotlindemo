package com.teayork.module_main.mvp.contact.home

import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract
import com.teayork.module_main.bean.ArticleBean
import com.teayork.module_main.bean.BannerBean

/**
 * author：pengzhixian on 2019-08-12 16:08
 * e-mail：759560522@qq.com
 */
interface HomeContact {
    interface IView : IBaseViewContract {
        fun setData(data: ArticleBean)
        fun setFail()
        fun setBanner(banner: MutableList<BannerBean>)
//        fun collectResult(id: Int)
//        fun cancelCollectResult(id: Int)
    }

    interface IPresenter : IPresenterContract {
        fun getData(page: Int,key: String)
        fun getBannerHttp()
//        fun collectHttp(id: Int)
//        fun cancelCollectHttp(id: Int)
    }
}