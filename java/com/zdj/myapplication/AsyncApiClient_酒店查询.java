//
//  Created by  fred on 2017/1/12.
//  Copyright © 2016年 Alibaba. All rights reserved.
//

package com.zdj.myapplication;

import com.alibaba.cloudapi.sdk.BaseApiClient;
import com.alibaba.cloudapi.sdk.BaseApiClientBuildParam;
import com.alibaba.cloudapi.sdk.CallMethod;
import com.alibaba.cloudapi.sdk.HttpConstant;
import com.alibaba.cloudapi.sdk.SdkException;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;


public class AsyncApiClient_酒店查询 extends BaseApiClient {
    public final static String HOST = "ali-hotel.showapi.com";

    public static AsyncApiClient_酒店查询 build(BaseApiClientBuildParam buildParam){
        if(null == buildParam){
            throw new SdkException("buildParam must not be null");
        }

        buildParam.check();
        return  new AsyncApiClient_酒店查询(buildParam);
    }

    private AsyncApiClient_酒店查询(BaseApiClientBuildParam buildParam){
        super(buildParam);
    }



    public void 全国酒店省份id查询(Callback callback) {
        String path = "/provList";
        
        httpGet(HttpConstant.CLOUDAPI_HTTPS , HOST , path , null , null , null , callback , CallMethod.ASYNC);
    }
    public void 根据省份查询城市id(String provinceId , Callback callback) {
        String path = "/cityList";
        Map<String , String>  queryParams = new HashMap<>();
        queryParams.put("provinceId",provinceId);

        httpGet(HttpConstant.CLOUDAPI_HTTPS , HOST , path , null , queryParams , null , callback , CallMethod.ASYNC);
    }
    public void 根据城市id查询行政区(String cityId , Callback callback) {
        String path = "/townList";
        Map<String , String>  queryParams = new HashMap<>();
        queryParams.put("cityId",cityId);

        httpGet(HttpConstant.CLOUDAPI_HTTPS , HOST , path , null , queryParams , null , callback , CallMethod.ASYNC);
    }
    public void 酒店查询(String pageSize,String cityId , String leaveDate , String comeDate , Callback callback) {
        String path = "/hotelList";
        Map<String , String>  queryParams = new HashMap<>();
        queryParams.put("cityId",cityId);
        queryParams.put("leaveDate",leaveDate);
        queryParams.put("comeDate",comeDate);
        queryParams.put("pageSize",pageSize);
        httpGet(HttpConstant.CLOUDAPI_HTTPS , HOST , path , null , queryParams , null , callback , CallMethod.ASYNC);
    }
    public void 酒店价格查询(String comeDate , String leaveDate , String hotelId , Callback callback) {
        String path = "/hotelPrice";
        Map<String , String>  queryParams = new HashMap<>();
        queryParams.put("comeDate",comeDate);
        queryParams.put("leaveDate",leaveDate);
        queryParams.put("hotelId",hotelId);

        httpGet(HttpConstant.CLOUDAPI_HTTPS , HOST , path , null , queryParams , null , callback , CallMethod.ASYNC);
    }
}