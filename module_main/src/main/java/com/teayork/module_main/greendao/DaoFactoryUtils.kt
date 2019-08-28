package com.hankkin.reading.dao

import com.teayork.module_main.mvp.contact.search.SearchDaoContract
import com.teayork.module_main.mvp.model.SearchDao


/**
 * @author Hankkin
 * @date 2018/08/10
 */
object DaoFactoryUtils {

    fun <T> getDao(clazz: Class<T>): T = when (clazz) {

        SearchDaoContract::class.java -> SearchDao()

        else -> throw ClassNotFoundException("Not found : ${clazz.name}")
    } as T
}