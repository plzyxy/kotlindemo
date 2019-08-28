package com.teayork.module_main.mvp.presenter.search

import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.module_main.http.HttpClientUtils
import com.teayork.module_main.mvp.contact.search.SearchResultContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * author：pengzhixian on 2019-08-27 12:12
 * e-mail：759560522@qq.com
 */
class SearchResultPresenter : RxLifePresenter<SearchResultContact.IView>(), SearchResultContact.IPresenter {
    override fun getSearchData(page:Int,key:String) {
        HttpClientUtils.Builder.getCommonHttp()
            .query(page, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeNx({
                getMvpView().setSearchData(it.data)
            }, {
                getMvpView().setFail()
            }).bindRxLifeEx(RxLife.ON_DESTROY)}
}