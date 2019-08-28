package com.teayork.module_main.mvp.contact.search

import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract
import com.teayork.module_main.bean.ArticleBean

/**
 * author：pengzhixian on 2019-08-27 12:08
 * e-mail：759560522@qq.com
 */
interface SearchResultContact {
    interface IView : IBaseViewContract {
        fun setSearchData(data: ArticleBean)
        fun setFail()
    }

    interface IPresenter : IPresenterContract {
        fun getSearchData(page:Int,key:String)
    }
}