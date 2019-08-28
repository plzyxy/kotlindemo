package com.teayork.module_main.mvp.contact.search

import com.hankkin.reading.dao.BaseDaoContract
import com.teayork.module_main.daobean.HotBean

interface SearchDaoContract : BaseDaoContract {

    fun insertHot(hotBean: HotBean)

    fun query(): MutableList<HotBean>

    fun delete(id: Long)

}