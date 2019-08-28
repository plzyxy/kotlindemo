package com.teayork.module_transation

import com.alibaba.android.arouter.facade.annotation.Route
import com.teayork.common_base.base.BaseActivity
import com.teayork.common_base.constans.ARouterConfig

/**
 * author：pengzhixian on 2019-07-24 10:38
 * e-mail：759560522@qq.com
 */
@Route(path=ARouterConfig.TRANSATION_ACTIVITY)
class TransationActivity  :BaseActivity(){
    override fun getLayoutId(): Int {

    return R.layout.transation_activity_transation
    }

    override fun initView() {
    }
}