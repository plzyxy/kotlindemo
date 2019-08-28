package com.teayork.module_main.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.flexbox.FlexboxLayout;
import com.teayork.common_base.base.BaseMvpActivity;
import com.teayork.common_base.event.EventMap;
import com.teayork.common_base.utils.LogUtils;
import com.teayork.module_main.R;
import com.teayork.module_main.adapter.SearchHistoryAdapter;
import com.teayork.module_main.daobean.HotBean;
import com.teayork.module_main.mvp.contact.search.SearchContract;
import com.teayork.module_main.mvp.presenter.search.SearchPresenter;
import lombok.val;
import magicasakura.widgets.TintTextView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * author：pengzhixian on 2019-08-27 09:40
 * e-mail：759560522@qq.com
 */
public class SearchAllActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.IView {

    SearchHistoryAdapter mHistoryAdapter;
    RecyclerView rv_search_history;
    EditText et_search;
    FlexboxLayout flex;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_search;
    }

    @Override
    protected void initView() {

        et_search = findViewById(R.id.et_search);
        rv_search_history = findViewById(R.id.rv_search_history);
        flex = findViewById(R.id.flex);
        rv_search_history.setLayoutManager(new LinearLayoutManager(this));
        mHistoryAdapter = new SearchHistoryAdapter();
        rv_search_history.setAdapter(mHistoryAdapter);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                LogUtils.e("asssadasdasdsdsad");
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    HotBean hotBean = new HotBean();
                    hotBean.setName(v.getText() + "");
//                Long.parseLong(Math.random()+"");
//                    hotBean.setId(new Double(Math.random()).longValue());
                    getPresenter().insertDao(hotBean);
                goSearch(v.getText().toString());
                }


                return false;
            }
        });

    }

    void goSearch(String key){
      Intent intent=  new Intent(this,SearchResultActivity.class);
        intent.putExtra("key",key);
        startActivity(intent);
//        getPresenter().queryDao();



    }

    @Override
    public void initData() {
        getPresenter().getHotHttp();
        getPresenter().queryDao();
    }


    @NotNull
    @Override
    public Class<? extends SearchPresenter> registerPresenter() {
        return SearchPresenter.class;
    }

    @Override
    public void getHotResult(@NotNull List<HotBean> data) {

        for (HotBean hot : data) {
            TintTextView item = (TintTextView) LayoutInflater.from(this).inflate(R.layout.main_search_layout_hot_item, null);

            item.setText(hot.getName());
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().insertDao(hot);
                    goSearch(hot.getName());
                }
            });

            flex.addView(item);
        }

    }

    @Override
    public void onEventBus(@NotNull EventMap.BaseEvent event) {

        if (event instanceof EventMap.SearchHistoryDeleteEvent) {
            if (((EventMap.SearchHistoryDeleteEvent) event).getPosition() != -1) {
                int position = ((EventMap.SearchHistoryDeleteEvent) event).getPosition();
                HotBean hotBean = mHistoryAdapter.getData().get(position);
                getPresenter().delete(hotBean.getId());
            }

        }
    }

    @Override
    protected boolean regEvent() {
        return true;
    }

    @Override
    public void insertDao(long id) {
        LogUtils.e(">>>>DB" + "插入数据库成功");
        getPresenter().queryDao();

    }

    @Override
    public void queryResult(@NotNull List<HotBean> hotBean) {
        mHistoryAdapter.clear();
        mHistoryAdapter.addAll(hotBean);
        mHistoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void deleteResult() {
        LogUtils.e(">>>>DB" + "删除数据库成功");
        getPresenter().queryDao();

    }
}
