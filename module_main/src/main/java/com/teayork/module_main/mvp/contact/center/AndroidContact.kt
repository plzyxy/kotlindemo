package com.teayork.module_main.mvp.contact.center

import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract
import com.teayork.module_main.bean.ArticleBean

interface AndroidContact {
    interface IView : IBaseViewContract {
        fun setArticle(articleBean: ArticleBean)
        fun setFail()
        fun collectResult(id: Int)
        fun cancelCollectResult(id: Int)
    }

    interface IPresenter : IPresenterContract {
        fun getArticle(page: Int)
        fun collectHttp(id: Int)
        fun cancelCollectHttp(id: Int)
    }
}