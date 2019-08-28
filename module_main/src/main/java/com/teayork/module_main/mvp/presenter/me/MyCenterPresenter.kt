package com.teayork.module_main.mvp.presenter.me

import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.module_main.mvp.contact.me.MyCenterContact

/**
 * author：pengzhixian on 2019-08-08 16:12
 * e-mail：759560522@qq.com
 */
class MyCenterPresenter: RxLifePresenter<MyCenterContact.IView>(), MyCenterContact.IPresenter {
    override fun getData(page: Int) {

        val list = ArrayList<String>()
        list.run {
            add("aaaaa")
            add("bbbb")
            add("cccc")

        }

//        getMvpView().setData(list)
        getMvpView().setFail()

//        HttpClientUtils.Builder.getCommonHttp()
//            .cancelCollectArticle(page)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeNx ({
//                getMvpView().setData("aaa")
//            },{
//                getMvpView().setFail()
//            }).bindRxLifeEx(RxLife.ON_DESTROY)

    }


}