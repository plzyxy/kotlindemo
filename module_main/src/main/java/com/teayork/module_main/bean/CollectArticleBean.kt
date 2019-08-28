package com.teayork.module_main.bean

data class CollectArticleBean(val curPage: Int,
                              val datas: MutableList<CollectArticleBean>,
                              val offset: Int,
                              val over: Boolean,
                              val pageCount: Int,
                              val size: Int,
                              val total: Int)