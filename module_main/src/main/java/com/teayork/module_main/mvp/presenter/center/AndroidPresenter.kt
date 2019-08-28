package com.teayork.module_main.mvp.presenter.center

import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.module_main.http.HttpClientUtils
import com.teayork.module_main.mvp.contact.center.AndroidContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AndroidPresenter : RxLifePresenter<AndroidContact.IView>(), AndroidContact.IPresenter {
    override fun cancelCollectHttp(id: Int) {
        HttpClientUtils.Builder.getCommonHttp()
                .cancelCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeNx ({
                    getMvpView().cancelCollectResult(id)
                },{
                    getMvpView().setFail()
                }).bindRxLifeEx(RxLife.ON_DESTROY)
    }

    override fun collectHttp(id: Int) {
        HttpClientUtils.Builder.getCommonHttp()
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeNx ({
                    getMvpView().collectResult(id)
                },{
                    getMvpView().setFail()
                }).bindRxLifeEx(RxLife.ON_DESTROY)
    }

    override fun getArticle(page: Int) {
        HttpClientUtils.Builder.getCommonHttp()
                .getArticle(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeNx ({
                    getMvpView().setArticle(it.data)
                },{
                    getMvpView().setFail()
                }).bindRxLifeEx(RxLife.ON_DESTROY)
    }


}