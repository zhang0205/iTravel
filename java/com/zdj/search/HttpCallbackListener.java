package com.zdj.search;

/**
 * Created by Administrator on 2017/7/28 0028.
 */

public interface HttpCallbackListener {

    void onFinish(String response);
    void onError(Exception e);
}
