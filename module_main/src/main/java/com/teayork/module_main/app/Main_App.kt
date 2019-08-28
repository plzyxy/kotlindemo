package com.teayork.module_main.app

import com.teayork.common_base.base.BaseApplication
import com.teayork.module_main.dao.DaoSession
import com.teayork.module_main.dao.DaoMaster
import android.database.sqlite.SQLiteDatabase
import com.teayork.common_base.constans.Constant
import com.teayork.module_main.greendao.GreenOpenHelper


/**
 * author：pengzhixian on 2019-07-17 18:30
 * e-mail：759560522@qq.com
 */
open class Main_App :BaseApplication() {

    private lateinit var daoSession: DaoSession
    companion object {
        private var instance: Main_App? = null

        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance=this
        initgreendao()

    }
    private fun initgreendao() {
        val devOpenHelper = GreenOpenHelper(this, Constant.Common.DB_NAME, null)
        val daoMaster = DaoMaster(devOpenHelper.writableDb)
        daoSession = daoMaster.newSession()
    }

    fun getDaoSession(): DaoSession {
        return daoSession
    }


}