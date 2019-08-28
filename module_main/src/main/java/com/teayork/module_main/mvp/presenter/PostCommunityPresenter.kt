package com.teayork.module_main.mvp.presenter

import com.teayork.common_base.mvp.presenter.RxLifePresenter
import com.teayork.module_main.mvp.contact.PostCommunityContact

/**
 * author：pengzhixian on 2019-08-19 11:41
 * e-mail：759560522@qq.com
 */
class PostCommunityPresenter: RxLifePresenter<PostCommunityContact.IView>(),PostCommunityContact.IPresenter {
    override fun getData(page: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
