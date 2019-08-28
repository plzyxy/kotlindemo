package com.teayork.common_base.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebSettingsImpl
import com.just.agentweb.DefaultWebClient
import com.teayork.common_base.R
import com.teayork.common_base.utils.ToastUtils
import kotlinx.android.synthetic.main.common_activity_basebwebview.*

/**
 * author：pengzhixian on 2019-08-06 16:11
 * e-mail：759560522@qq.com
 */
 class BaseBWebViewActivity : BaseSwipeBackActivity() {


    private lateinit var mUrl: String
    private lateinit var mTitle: String
    private lateinit var mAgentWeb: AgentWeb


    companion object {
        fun loadUrl(context: Context, url: String, title: String) {
            val intent = Intent(context, BaseBWebViewActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("title", title)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.common_activity_basebwebview
    }

    override fun initView() {

//        setStatusBarColor()
        getIntentData()
        initWebView()
        initToolBar()
        menuClick()

    }

    private fun getIntentData() {
        if (intent != null) {
            mTitle = intent.getStringExtra("title")
            mUrl = intent.getStringExtra("url")
        }
    }

    @SuppressLint("RestrictedApi")
    private fun initToolBar() {
        toobar_article_detail.apply {
            setSupportActionBar(this)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.openOptionsMenu()
        }

        tv_article_detail_title.apply {
            text = mTitle
            postDelayed({ tv_article_detail_title.isSelected = true }, 2000)
        }
    }

    private fun initWebView() {


//        mAgentWeb = AgentWeb.with(this)//传入Activity
//            .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
//        .useDefaultIndicator()// 使用默认进度条
//            .defaultProgressBarColor() // 使用默认进度条颜色
//            .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
//            .createAgentWeb()//
//            .ready()
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(ll_common_web, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setAgentWebWebSettings(AgentWebSettingsImpl.getInstance())
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .interceptUnkownUrl()
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .go(mUrl)
    }


    fun menuClick() {
        toobar_article_detail.setOnMenuItemClickListener { item ->
            ToastUtils.showToast(this,"点击")
            when (item.itemId) {
//                R.id.menu_share ->
//                R.id.menu_open ->
//                R.id.menu_share -> CommonUtils.share(this@CommonWebActivity, mUrl)
//                R.id.menu_open -> CommonUtils.openBroswer(this@CommonWebActivity, mUrl)
                else -> {
                    false
                }
            }

            true
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.article_detail_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }




}