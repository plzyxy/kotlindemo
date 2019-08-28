package com.teayork.module_main.mvp.model

import com.teayork.module_main.daobean.HotBean
import com.teayork.module_main.greendao.BaseDao
import com.teayork.module_main.mvp.contact.search.SearchDaoContract

class SearchDao : BaseDao(), SearchDaoContract {

    override fun query(): MutableList<HotBean> =
        daoSession.hotBeanDao!!.queryBuilder()!!.list()

    override fun delete(id: Long) {
        daoSession.hotBeanDao.deleteByKey(id)
    }

    override fun insertHot(hotBean: HotBean) {
        daoSession.hotBeanDao.insertOrReplace(hotBean)
    }


}
