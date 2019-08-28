package com.teayork.common_base.manage

import android.app.Activity
import java.util.HashSet

/**
 * author：pengzhixian on 2019-07-17 17:12
 * e-mail：759560522@qq.com
 */
class ActivityManage {


    //保存所有创建的Activity
    private val allActivities = HashSet<Activity>()

    /**
     * 添加Activity到管理器
     *
     * @param activity activity
     */
    fun addActivity(activity: Activity?) {
        if (activity != null) {
            allActivities.add(activity)
        }
    }


    /**
     * 从管理器移除Activity
     *
     * @param activity activity
     */
    fun removeActivityty(activity: Activity?) {
        if (activity != null) {
            allActivities.remove(activity)
        }
    }

    /**
     * 关闭所有Activity
     */
    fun finishAll() {
        for (activity in allActivities) {
            activity.finish()
        }

    }
}