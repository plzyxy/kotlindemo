package com.teayork.module_main.mvp.presenter.center

import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.module_main.http.HttpClientUtils
import com.teayork.module_main.mvp.contact.center.CenterTestContact
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.functions.Action1
import java.util.concurrent.TimeUnit

/**
 * author：pengzhixian on 2019-08-09 10:11
 * e-mail：759560522@qq.com
 */
class CenterTestPresenter: RxLifePresenter<CenterTestContact.IView>(),CenterTestContact.IPresenter {
    override fun getData(cate: String, page: Int) {
        val list = ArrayList<String>()
        HttpClientUtils.Builder.getGankHttp().
            getGank(cate,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getMvpView().setData(it)
            }
            .bindRxLifeEx(RxLife.ON_DESTROY)




    }
}