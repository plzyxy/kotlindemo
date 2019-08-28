package com.teayork.common_base.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.Unbinder
import com.gyf.barlibrary.ImmersionBar
import com.teayork.common_base.R
import com.teayork.common_base.event.EventMap
import io.reactivex.annotations.NonNull
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 * author：pengzhixian on 2019-07-17 16:57
 * e-mail：759560522@qq.com
 */
abstract class  BaseActivity :AppCompatActivity() , EasyPermissions.PermissionCallbacks {

    private var unbinder: Unbinder? = null
    protected var mImmersionBar: ImmersionBar? = null
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isActionBar()) {
            setContentView(R.layout.common_activity_base)
            (findViewById<View>(R.id.fl_content) as ViewGroup).addView(layoutInflater.inflate(getLayoutId(), null))
        } else {
            setContentView(getLayoutId())
        }
        unbinder = ButterKnife.bind(this)
        mContext=this
        //初始化ButterKnife
        //沉浸式状态栏
        initImmersionBar(R.color.theme)
        //加入Activity管理器
        BaseApplication.instance.getActivityManage().addActivity(this)
        if (regEvent()) {
            EventBus.getDefault().register(this)
        }
        initView()

    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }


    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }




    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.i("EasyPermissions", "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                .setPositiveButton("好")
                .setNegativeButton("不行")
                .build()
                .show()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        if (unbinder != null) {
            unbinder!!.unbind()
        }
        if (regEvent()) {
            EventBus.getDefault().unregister(this)
        }
        //必须调用该方法，防止内存泄漏
        if (mImmersionBar != null) {
            mImmersionBar!!.destroy()
        }
        //将Activity从管理器移除
        BaseApplication.instance.getActivityManage().removeActivityty(this)
    }

    /**
     * 沉浸栏颜色
     */
    protected fun initImmersionBar(color: Int) {
        if (mImmersionBar == null) {
            mImmersionBar = ImmersionBar.with(this)
            if (color != 0) {
                mImmersionBar!!.statusBarColor(color).fitsSystemWindows(true)
            }
            mImmersionBar!!.init()
        }
    }


    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onEventBus(event: EventMap.BaseEvent) {
    }

    /**
     * 是否需要ActionBar
     * TODO 暂时用此方法 后续优化
     */
    protected fun isActionBar(): Boolean {
        return false
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected open fun regEvent(): Boolean {
        return false
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()
}