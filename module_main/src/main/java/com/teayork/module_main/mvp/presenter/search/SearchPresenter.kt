package com.teayork.module_main.mvp.presenter.search

import com.hankkin.reading.dao.DaoFactory
import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.module_main.daobean.HotBean
import com.teayork.module_main.http.HttpClientUtils
import com.teayork.module_main.mvp.contact.search.SearchContract
import com.teayork.module_main.mvp.contact.search.SearchDaoContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * author：pengzhixian on 2019-08-27 07:12
 * e-mail：759560522@qq.com
 */
class SearchPresenter : RxLifePresenter<SearchContract.IView>(), SearchContract.IPresenter {

    override fun delete(id: Long) {
        DaoFactory.getProtocol(SearchDaoContract::class.java).delete(id)
        getMvpView().deleteResult()
    }

    override fun insertDao(hotBean: HotBean) {
        DaoFactory.getProtocol(SearchDaoContract::class.java).insertHot(hotBean)
        getMvpView().insertDao(hotBean.id)
    }

    override fun queryDao() {
        val hotBean = DaoFactory.getProtocol(SearchDaoContract::class.java).query()
        getMvpView().queryResult(hotBean)
    }


    override fun getHotHttp() {
        HttpClientUtils.Builder.getCommonHttp()
                .getHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeNx({
                    getMvpView().getHotResult(it.data)
                },{
                }).bindRxLifeEx(RxLife.ON_DESTROY)
    }




}