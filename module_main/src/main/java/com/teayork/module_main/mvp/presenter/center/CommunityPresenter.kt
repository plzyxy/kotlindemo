package com.teayork.module_main.mvp.presenter.center

import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.module_main.mvp.contact.center.CommunityContact
import com.teayork.module_main.mvp.model.Moment
import java.util.*
import kotlin.collections.ArrayList

/**
 * author：pengzhixian on 2019-08-15 11:49
 * e-mail：759560522@qq.com
 */
class CommunityPresenter: RxLifePresenter<CommunityContact.IView>(),CommunityContact.IPresenter {
    override fun getData(page: Int) {
        val moments = java.util.ArrayList<Moment>()

        moments.add(
            Moment(
                "1张网络图片",
                ArrayList<String>(Arrays.asList<String>("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered1.png"))
            )
        )
        moments.add(
            Moment(
                "2张网络图片",
                ArrayList<String>(
                    Arrays.asList<String>(
                        "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered2.png",
                        "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered3.png"
                    )
                )
            )
        )

        getMvpView().setData(moments)







    }
}