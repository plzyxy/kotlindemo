package com.teayork.module_main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.teayork.common_base.base.BaseBWebViewActivity
import com.teayork.common_base.base.recycleview.BaseRecyclerViewAdapter
import com.teayork.common_base.base.recycleview.BaseRecyclerViewHolder
import com.teayork.common_base.constans.Constant
import com.teayork.common_base.utils.RxBusTools
import com.teayork.common_base.utils.SPUtils
import com.teayork.common_base.utils.ToastUtils
import com.teayork.module_main.R
import com.teayork.module_main.bean.ArticleDetailBean
import magicasakura.widgets.TintImageView
import magicasakura.widgets.TintTextView

class AndroidAdapter : BaseRecyclerViewAdapter<ArticleDetailBean>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder<*> {
        return ViewHolder(parent, R.layout.main_adapter_android_item)
    }


    private class ViewHolder(parent: ViewGroup, layoutId: Int) : BaseRecyclerViewHolder<ArticleDetailBean>(parent, layoutId) {

        val tvAuthor by lazy { itemView.findViewById<TextView>(R.id.tv_adapter_android_author) }
        val tvDesc by lazy { itemView.findViewById<TextView>(R.id.tv_adapter_android_desc) }
        val tvChapter by lazy { itemView.findViewById<TextView>(R.id.tv_adapter_android_chapter) }
        val tvTime by lazy { itemView.findViewById<TextView>(R.id.tv_adapter_android_time) }
        val ivPic by lazy { itemView.findViewById<ImageView>(R.id.iv_adapter_android_pic) }
        val llTags: LinearLayout by lazy { itemView.findViewById<LinearLayout>(R.id.ll_adapter_android_tags) }
        val ivCollect by lazy { itemView.findViewById<ImageView>(R.id.iv_adapter_android_collect_normal) }
        val ivCollected by lazy { itemView.findViewById<TintImageView>(R.id.iv_adapter_android_collected) }
        val ll by lazy { itemView.findViewById<LinearLayout>(R.id.ll_adapter_android) }

        override fun onBindViewHolder(bean: ArticleDetailBean?, position: Int) {
            bean?.apply {
                tvAuthor.text = author

                tvChapter.text = if (superChapterName == null) chapterName else superChapterName + " / " + chapterName
                tvDesc.text = if (desc.isEmpty()) title else desc
                tvTime.text = niceDate
                if (envelopePic.isEmpty()) {
                    ivPic.visibility = View.GONE
                } else {
                    if (SPUtils.getInt(Constant.SP_KEY.WIFI_IMG) != 0) {
                        ivPic.visibility = View.VISIBLE
                        Glide.with(ivPic.context).load(envelopePic).into(ivPic)
                    } else {
                        ivPic.visibility = View.VISIBLE
                        ivPic.setImageResource(R.mipmap.bg_default)
                    }
                }
                llTags.removeAllViews()
                if (tags != null) {
                    for (s in tags) {
                        llTags.addView((LayoutInflater.from(llTags.context).inflate(R.layout.main_layout_adapter_android_tag, null) as TintTextView).apply {
                            text = s.name
                        })
                    }
                    if (fresh) {
                        llTags.addView((LayoutInflater.from(llTags.context).inflate(R.layout.main_layout_adapter_android_tag, null) as TintTextView).apply {
                            text = "新"
                        })
                    }
                }

                ll.setOnClickListener {
//                    ToastUtils.showToast(itemView.context,"跳转 webview")


                    BaseBWebViewActivity.loadUrl(itemView.context, link, title)
                }
                    //用户登录收藏
//                if (!UserControl.isLogin()) {
//                    ivCollect.visibility = View.VISIBLE
//                    ivCollected.visibility = View.GONE
//                } else {
//                    if (UserControl.getCurrentUser()!!.collectIds.contains(id.toString()) || UserControl.getCurrentUser()!!.collectIds.contains(originId.toString())) {
//                        ivCollected.visibility = View.VISIBLE
//                        ivCollect.visibility = View.GONE
//                    } else {
//                        ivCollect.visibility = View.VISIBLE
//                        ivCollected.visibility = View.GONE
//                    }
//                }
//                ivCollect.setOnClickListener {
//                    if (!UserControl.isLogin()) {
//                        ivCollect.context.startActivity(Intent(ivCollect.context, LoginActivity::class.java))
//                    } else {
//                        ivCollect.visibility = View.GONE
//                        ivCollected.visibility = View.VISIBLE
//                        val id = id ?: originId
//                        RxBusTools.getDefault().post(EventMap.CollectEvent(EventMap.CollectEvent.COLLECT, id))
//                    }
//                }
//                ivCollected.setOnClickListener {
//                    if (!UserControl.isLogin()) {
//                        ivCollect.context.startActivity(Intent(ivCollect.context, LoginActivity::class.java))
//                    } else {
//                        ivCollect.visibility = View.VISIBLE
//                        ivCollected.visibility = View.GONE
//                        val id = id ?: originId
//                        RxBusTools.getDefault().post(EventMap.CollectEvent(EventMap.CollectEvent.UNCOLLECT, id))
//                    }
//                }
            }

        }

    }
}