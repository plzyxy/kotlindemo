package com.teayork.module_main.app

import android.app.Application
import android.content.Context
import com.teayork.common_base.base.BaseApplication
import com.teayork.module_main.dao.DaoSession
import com.teayork.module_main.dao.DaoMaster
import android.database.sqlite.SQLiteDatabase
import com.teayork.common_base.base.IComponentApplication
import com.teayork.common_base.constans.Constant
import com.teayork.common_base.utils.LogUtils
import com.teayork.module_main.greendao.GreenOpenHelper


/**
 * author：pengzhixian on 2019-07-17 18:30
 * e-mail：759560522@qq.com
 */
open class Main_App :Application(), IComponentApplication  {
    private lateinit var daoSession: DaoSession
    private lateinit var context: Context
    private lateinit var application: Application
    companion object {
        private var instance: Main_App? = null

        fun instance() = instance!!
    }


    override fun init(app: Application) {

        LogUtils.e("main  chu shi   hua  .... ")

        instance =this
        application=app
        context=app.applicationContext
        initgreendao(app)
    }
    private fun initgreendao(app:Application) {
        val devOpenHelper = GreenOpenHelper(app, Constant.Common.DB_NAME, null)
        val daoMaster = DaoMaster(devOpenHelper.writableDb)
        daoSession = daoMaster.newSession()
    }

    fun getDaoSession(): DaoSession {
        return daoSession
    }


}