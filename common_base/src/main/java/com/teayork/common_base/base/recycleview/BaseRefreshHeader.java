package com.teayork.common_base.base.recycleview;

/**
 * Created by jianghejie on 15/11/22.
 */
interface BaseRefreshHeader {
    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplate();

    int getVisiableHeight();
}
