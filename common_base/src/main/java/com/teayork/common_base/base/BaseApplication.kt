package com.teayork.common_base.base

import android.app.Application
import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper
import com.alibaba.android.arouter.launcher.ARouter
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.hankkin.library.utils.AppUtils
import com.hankkin.library.utils.FileUtils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogcatLogStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.teayork.common_base.BuildConfig
import com.teayork.common_base.R
import com.teayork.common_base.constans.FileConfig
import com.teayork.common_base.http.HttpConfig
import com.teayork.common_base.manage.ActivityManage
import com.teayork.common_base.utils.SPUtils
import com.teayork.common_base.utils.ThemeHelper
import com.teayork.common_base.utils.ThemeHelper.*
import com.teayork.common_base.utils.toast.ToastUtils
import com.teayork.common_base.widget.toasty.Toasty
import magicasakura.utils.ThemeUtils
import kotlin.properties.Delegates


/**
 * author：pengzhixian on 2019-07-17 16:35
 * e-mail：759560522@qq.com
 */
open class BaseApplication : Application(), ThemeUtils.switchColor {


    private val MODULESLIST = arrayOf("com.teayork.module_main.app.Main_App")


    private fun moduleApplicationInit(baseApplication: BaseApplication) {
        for (moduleImpl in MODULESLIST) {
            try {
                val clazz = Class.forName(moduleImpl)
                val obj = clazz.newInstance()
                if (obj is IComponentApplication) {
                    obj.init(baseApplication)
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }

        }


    }


    override fun replaceColorById(context: Context, @ColorRes colorId: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) {
            return context.resources.getColor(colorId)
        }
        val theme = getTheme(context)
        var colorIdTemp = colorId
        if (theme != null) {
            colorIdTemp = getThemeColorId(context, colorId, theme)
        }
        return context.resources.getColor(colorIdTemp)
    }

    override fun replaceColor(context: Context, @ColorInt originColor: Int): Int {
        if (ThemeHelper.isDefaultTheme(context)) {
            return originColor
        }
        val theme = getTheme(context)
        var colorId = -1

        if (theme != null) {
            colorId = getThemeColor(context, originColor.toLong(), theme)
        }
        return if (colorId != -1) resources.getColor(colorId) else originColor
    }

    private lateinit var activityManage: ActivityManage

    companion object {
        // 单例不会是null   所以使用notNull委托
        var instance: BaseApplication by Delegates.notNull()

    }

    override fun onCreate() {
        super.onCreate()
        instance = this;
        activityManage = ActivityManage()
        FileUtils.initSd(AppUtils.getAppName(this)!!)
        initARouter()
        ToastUtils.init(this)
        SPUtils.init(this, FileConfig.SPFILENAME)
        initHttp()
        initCommon()
        moduleApplicationInit(this)

    }

    /**
     * 初始化日志打印框架
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)                   //（可选）是否显示线程信息。 默认值为true
            .methodCount(2)                          //（可选）要显示的方法行数。 默认2
            .methodOffset(7)                         //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
            .logStrategy(LogcatLogStrategy())    //（可选）更改要打印的日志策略。 默认LogCat
            .tag("app")                              //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                //DEBUG模式下不打印LOG
                return BuildConfig.DEBUG
            }
        })
    }

    /**
     * 初始化路由
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()  // 打印日志
            ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(instance)// 尽可能早，推荐在Application中初始化
    }

    /**
     * 程序终止的时候执行
     */
    override fun onTerminate() {
        super.onTerminate()
        exitApp()
    }


    private fun initHttp() {
        HttpConfig.setCookie(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this)))
    }

    private fun initCommon() {
        ThemeUtils.setSwitchColor(this)
        BGASwipeBackHelper.init(this, null)
        Toasty.Config.getInstance()
            .setInfoColor(resources.getColor(ThemeHelper.getCurrentColor(this)))
            .apply(this)
    }


    private fun getTheme(context: Context): String? {
        return when (ThemeHelper.getTheme(context)) {
            COLOR_YIMA -> "yima"
            COLOR_KUAN -> "kuan"
            COLOR_BILI -> "bili"
            COLOR_YIDI -> "yidi"
            COLOR_SHUIYA -> "shuiya"
            COLOR_YITENG -> "yiteng"
            COLOR_JILAO -> "jilao"
            COLOR_ZHIHU -> "zhihu"
            COLOR_GUTONG -> "gutong"
            COLOR_DIDIAO -> "didiao"
            COLOR_GAODUAN -> "gaoduan"
            COLOR_APING -> "aping"
            COLOR_LIANGBAI -> "liangbai"
            COLOR_ANLUOLAN -> "anluolan"
            COLOR_XINGHONG -> "xinghong"
            else -> {
                "yima"
            }
        }
    }


    @ColorRes
    private fun getThemeColorId(context: Context, colorId: Int, theme: String): Int {
        when (colorId) {
            R.color.theme_color_primary -> return context.resources.getIdentifier(theme, "color", packageName)
            R.color.theme_color_primary_dark -> return context.resources.getIdentifier(
                theme + "_dark",
                "color",
                packageName
            )
            R.color.colorAccent -> return context.resources.getIdentifier(theme + "_accent", "color", packageName)
        }
        return colorId
    }

    @ColorRes
    private fun getThemeColor(context: Context, color: Long, theme: String): Int {
        when (color) {
            0xfff44336 -> return context.resources.getIdentifier(theme, "color", packageName)
            0xfff44336 -> return context.resources.getIdentifier(theme + "_dark", "color", packageName)
            0xfff44336 -> return context.resources.getIdentifier(theme + "_accent", "color", packageName)
        }
        return -1
    }

//    /**
//     * 获取全局唯一上下文
//     *
//     * @return BaseApplication
//     */
//    fun getApplication(): BaseApplication {
//        return application
//    }


    /**
     * 退出应用
     */
    fun exitApp() {
        activityManage.finishAll()
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }

    /**
     * 返回Activity管理器
     */
    fun getActivityManage(): ActivityManage {
        if (activityManage == null) {
            activityManage = ActivityManage()
        }
        return activityManage
    }


}