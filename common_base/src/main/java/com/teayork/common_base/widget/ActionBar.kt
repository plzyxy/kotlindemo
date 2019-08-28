package com.teayork.common_base.widget

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.teayork.common_base.R
import com.teayork.common_base.dp

/**
 * author：pengzhixian on 2019-07-17 17:36
 * e-mail：759560522@qq.com
 */
class ActionBar :RelativeLayout {


    private var llLeft: LinearLayout? = null
    private var llCenter: LinearLayout? = null
    private var llRight: LinearLayout? = null

    /**
     * 总布局
     */
//    private var rootView: View? = null
    var rootview:View? = null
    /**
     * 字体颜色
     */
    private var leftTextColor = R.color.white
    private var centerTextColor = R.color.white
    private var rightTextColor = R.color.white
    /**
     * 字体大小
     */
    private var lefTextSize = 16
    private var centerTextSize = 16
    private var rightTextSize = 16


    constructor(context: Context):this(context,null){

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context,attrs,0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs,defStyleAttr) {
        init(context, attrs)

    }


    private fun init(context: Context, attrs: AttributeSet?) {
         rootview = View.inflate(context, R.layout.action_bar, this)

        llLeft = findViewById<LinearLayout>(R.id.ll_actionbar_left)
        llCenter = findViewById<LinearLayout>(R.id.ll_actionbar_centre)
        llRight = findViewById<LinearLayout>(R.id.ll_actionbar_right)
        showBackImg()


    }
    /**
     * 设置左边图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    fun setLeftIcon(res: Int, l: View.OnClickListener) {
        setViewVisibility(llLeft, true)
        llLeft!!.removeAllViews()
        val ivLeft = ImageView(context)
        ivLeft.setImageResource(res)
        llLeft!!.addView(ivLeft)
        llLeft!!.setOnClickListener(l)
    }

    /**
     * 设置中间图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    fun setCenterIcon(res: Int, l: View.OnClickListener) {
        setViewVisibility(llCenter, true)
        llCenter!!.removeAllViews()
        val center = ImageView(context)
        center.setImageResource(res)
        llCenter!!.addView(center)
        llCenter!!.setOnClickListener(l)
    }

    /**
     * 设置右边图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    fun setRightIcon(res: Int, l: View.OnClickListener) {
        setViewVisibility(llRight, true)
        llRight!!.removeAllViews()
        val right = ImageView(context)
        right.setImageResource(res)
        llRight!!.addView(right)
        llRight!!.setOnClickListener(l)
    }

    /**
     * 设置左边文字
     *
     * @param text 文字
     * @param l    监听器
     */
    fun setLeftText(text: CharSequence, l: View.OnClickListener?) {
        setViewVisibility(llLeft, true)
        llLeft!!.removeAllViews()
        val left = getTextView()
        left.setText(text)
        left.setTextColor(resources.getColor(leftTextColor))
        left.setTextSize(lefTextSize.toFloat())
        llLeft!!.addView(left)
        if (l != null) {
            llLeft!!.setOnClickListener(l)
        }
    }

    /**
     * 设置左边文字
     *
     * @param text 文字
     */
    fun setLeftText(text: CharSequence) {
        setLeftText(text, null)
    }


    /**
     * 设置中间文字
     *
     * @param text 文字
     * @param l    监听器
     */
    fun setCenterText(text: CharSequence, l: View.OnClickListener?) {
        setViewVisibility(llCenter, true)
        llCenter!!.removeAllViews()
        val center = getTextView()
        center.setTextSize(centerTextSize.toFloat())
        center.setText(text)
        center.setEllipsize(TextUtils.TruncateAt.END)
        center.setMaxLines(1)
        center.setTextColor(resources.getColor(centerTextColor))
        llCenter!!.addView(center)
        if (l != null) {
            llCenter!!.setOnClickListener(l)
        }
    }

    /**
     * 设置中间文字
     *
     * @param text 文字
     */
    fun setCenterText(text: CharSequence) {
        setCenterText(text, null)
    }


    /**
     * 设置右边文字
     *
     * @param text 文字
     * @param l    监听器
     */
    fun setRightText(text: CharSequence, l: View.OnClickListener?) {
        setViewVisibility(llRight, true)
        llRight!!.removeAllViews()
        val tvRight = getTextView()
        tvRight.setText(text)
        tvRight.setTextSize(rightTextSize.toFloat())
        tvRight.setTextColor(ContextCompat.getColor(context, rightTextColor))
        llRight!!.addView(tvRight)
        if (l != null) {
            llRight!!.setOnClickListener(l)
        }
    }

    /**
     * 设置右边文字
     *
     * @param text 文字
     */
    fun setRightText(text: CharSequence) {
        setRightText(text, null)
    }


    /**
     * 得到右边的布局
     *
     * @return View
     */
    fun getRightView(): View {
        return llRight!!
    }

    /**
     * 得到中间的布局
     *
     * @return View
     */
    fun getCenterView(): View {
        return llCenter!!
    }

    /**
     * 得到左边的布局
     *
     * @return View
     */
    fun getLeftView(): View {
        return llLeft!!
    }

    /**
     * 设置左边布局
     *
     * @param v v
     */
    fun setLeftView(v: View) {
        setViewVisibility(llLeft, true)
        llLeft!!.removeAllViews()
        llLeft!!.addView(v)
    }

    /**
     * 设置中间布局
     *
     * @param v v
     */
    fun setCenterView(v: View) {
        setViewVisibility(llCenter, true)
        llCenter!!.removeAllViews()
        llCenter!!.addView(v)
    }

    /**
     * 设置右边的view
     *
     * @param v v
     */
    fun setRightView(v: View) {
        setViewVisibility(llRight, true)
        llRight!!.removeAllViews()
        llRight!!.addView(v)
    }

    /**
     * 显示默认的左边的按钮
     */
    fun showBackImg() {
        setLeftIcon(R.drawable.ic_back, OnClickListener {
            val ctx = this@ActionBar.context
            if (ctx is AppCompatActivity) {
                (ctx as AppCompatActivity).onBackPressed()
            }
        })
    }

    /**
     * 设置View是否显示
     *
     * @param v
     * @param visibility 是否显示
     */
    private fun setViewVisibility(v: View?, visibility: Boolean) {
        if (v != null) {
            v.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
        }
    }

    /**
     * 获取textView
     */
    fun getTextView(): TextView {
        val tv = TextView(context)
        tv.setTextSize(16f)
        return tv
    }

    /**
     * 获取imageView
     */
    fun getImageView(): ImageView {
        val iv = ImageView(context)
        iv.setLayoutParams(RelativeLayout.LayoutParams(25.dp, 25.dp))
        return iv

    }
}