package com.teayork.module_main.greendao

import com.teayork.module_main.app.Main_App
import com.teayork.module_main.dao.DaoSession

open class BaseDao{
    var daoSession: DaoSession
    init {
        this.daoSession = Main_App.instance().getDaoSession()
    }
}