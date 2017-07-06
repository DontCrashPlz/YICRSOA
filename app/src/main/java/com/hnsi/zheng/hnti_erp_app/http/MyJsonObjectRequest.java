package com.hnsi.zheng.hnti_erp_app.http;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义JsonObjectRequest实现Cookie自动填充，将MyApplication中的JSESSION参数添加到请求头中
 * Created by Zheng on 2016/4/22.
 */
public class MyJsonObjectRequest extends JsonObjectRequest {

    public MyJsonObjectRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public MyJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    public MyJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MyJsonObjectRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public MyJsonObjectRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String,String> headers=new HashMap<>();
        Log.e("LOG", MyApplication.JSESSIONID);
        headers.put("Cookie",MyApplication.JSESSIONID);
        return headers;
    }

}
