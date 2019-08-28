package com.teayork.common_base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hankkin.library.utils.AppUtils
import com.teayork.common_base.R
import com.teayork.common_base.contract.IPresenterContract
import com.teayork.common_base.event.EventMap
import com.teayork.common_base.mvp.view.MvpFragment
import com.teayork.common_base.utils.LogUtils
import com.teayork.common_base.widget.view.PageLayout
import io.reactivex.disposables.CompositeDisposable

abstract class BaseMvpFragment<out T : IPresenterContract> : MvpFragment<T>() {

    protected var TAG: String? = null

    protected var activity: Activity? = null

    private var isShowVisible = false

    private var isInitView = false

    private var isFirstLoad = true

    var disposables = CompositeDisposable()

    protected lateinit var mPageLayout: PageLayout



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        TAG = javaClass.simpleName
        activity = context as Activity?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init_(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    protected fun initPageLayout(targetView: Any, isShowLoading: Boolean = false){
        mPageLayout = PageLayout.Builder(context!!)
                .initPage(targetView)
                .setDefaultEmptyText(resources.getString(R.string.pagelayout_empty))
                .setDefaultErrorText(resources.getString(R.string.pagelayout_error))
                .setDefaultLoadingBlinkText(AppUtils.getAppName(this.context!!)!!)
                .create()
        if (isShowLoading) mPageLayout.showLoading()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initView()
//        registerEvent()
        isInitView = true
        lazyLoadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
//        LogUtils.e("setUserVisibleHint", "-> " +isVisibleToUser)
        if (isVisibleToUser) {
            isShowVisible = true
            lazyLoadData()
        } else isShowVisible = false

    }

    protected fun lazyLoadData() {
//        LogUtils.e("lazyLoadData", "-> lazyLoadData" )

        if (!isFirstLoad || !isShowVisible || !isInitView) return
        initData()
        isFirstLoad = false
    }

//    open fun isHasBus(): Boolean {
//        return false
//    }

//    protected fun registerEvent() {
//        if (isHasBus()) {
//            val disposable = RxBusTools.getDefault().register(EventMap.BaseEvent::class.java, Consumer { onEvent(it) })
//            disposables.add(disposable)
//        }
//    }

    open fun onEvent(event: EventMap.BaseEvent) {
    }

    open fun init_(savedInstanceState: Bundle?) {}

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object{
        val TITLE="title"
    }



}