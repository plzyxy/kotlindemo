package com.teayork.module_main.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.animation.AlphaAnimation
import com.teayork.common_base.base.BaseActivity
import com.teayork.module_main.R
import kotlinx.android.synthetic.main.main_activity_wel.*
import pub.devrel.easypermissions.EasyPermissions

/**
 * author：pengzhixian on 2019-07-17 18:36
 * e-mail：759560522@qq.com
 */
class WelActivity :BaseActivity() {


    private var alphaAnimation: AlphaAnimation?=null



    override fun getLayoutId(): Int {
        return  R.layout.main_activity_wel

    }

    override fun initView() {
        handler.sendEmptyMessageDelayed(0, 2000)
        checkPermission()


    }

    @SuppressLint("HandlerLeak")
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var intent = Intent()
            intent.setClass(this@WelActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
//        val perms = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
        val perms = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        EasyPermissions.requestPermissions(this, "app应用需要以下权限，请允许", 0, *perms)

    }


    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == 0) {
            if (perms.isNotEmpty()) {
                if (perms.contains(Manifest.permission.READ_PHONE_STATE)
                    && perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (alphaAnimation != null) {
                        iv_web_icon.startAnimation(alphaAnimation)
                    }
                }
            }
        }
    }
}