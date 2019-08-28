package com.teayork.module_main.mvp.contact.search

import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.mvp.contract.IBaseViewContract
import com.teayork.module_main.daobean.HotBean

interface SearchContract {

    interface IView : IBaseViewContract {
        fun getHotResult(data: MutableList<HotBean>)
        fun insertDao(id: Long)
        fun queryResult(hotBean: MutableList<HotBean>)
        fun deleteResult()
    }

    interface IPresenter : IPresenterContract {
        fun getHotHttp()
        fun insertDao(hotBean: HotBean)
        fun queryDao()
        fun delete(id: Long)
    }

}