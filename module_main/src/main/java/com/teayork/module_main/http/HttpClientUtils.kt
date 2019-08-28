package com.teayork.module_main.http

import com.teayork.common_base.constans.Constant
import com.teayork.module_main.http.api.GankApi
import com.teayork.module_main.http.api.WanAndroidApi

interface HttpClientUtils {

    object Builder {
        fun getCommonHttp(): WanAndroidApi {
            return ApiFactory.instance.create(WanAndroidApi::class.java, Constant.WanAndroidUrl.BASE_URL)
        }

//        fun getOsChinaHttp(): OsChinaApi {
//            return ApiFactory.instance.create(OsChinaApi::class.java, Constant.OSChinaUrl.BASE_URL)
//        }
//
//        fun getToolsHttp(): ToolsApi {
//            return ApiFactory.instance.create(ToolsApi::class.java, Constant.ToolsUrl.WEATHER_URL)
//        }
//
        fun getGankHttp(): GankApi {
            return ApiFactory.instance.create(GankApi::class.java,Constant.GankUrl.BASE_URL)
        }
    }
}