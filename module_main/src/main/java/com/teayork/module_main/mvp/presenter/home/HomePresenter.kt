package com.teayork.module_main.mvp.presenter.home

import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.common_base.utils.LogUtils
import com.teayork.module_main.http.HttpClientUtils
import com.teayork.module_main.mvp.contact.home.HomeContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * author：pengzhixian on 2019-08-12 16:09
 * e-mail：759560522@qq.com
 */
class HomePresenter: RxLifePresenter<HomeContact.IView>(),HomeContact.IPresenter {
    override fun getData(page: Int,key: String) {
        HttpClientUtils.Builder.getCommonHttp()
            .query(page, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeNx({
                getMvpView().setData(it.data)
            }, {
                getMvpView().setFail()
            }).bindRxLifeEx(RxLife.ON_DESTROY)}

    override fun getBannerHttp() {
        LogUtils.e("eee","getBanner")
        HttpClientUtils.Builder.getCommonHttp()
            .getHomeBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeNx({
                getMvpView().setBanner(it.data)
            }, {
                getMvpView().setFail()
            }).bindRxLifeEx(RxLife.ON_DESTROY)

    }

}