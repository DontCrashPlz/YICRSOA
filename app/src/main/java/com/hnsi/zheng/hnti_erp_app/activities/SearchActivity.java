package com.hnsi.zheng.hnti_erp_app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.adapters.DepartmentDetailAdapter;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentDetailEntity;
import com.hnsi.zheng.hnti_erp_app.database.ConstactsInfoTableHelper;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录搜索界面
 * Created by Zheng on 2016/9/9.
 */
public class SearchActivity extends MyBaseActivity{

    private EditText mEdittext;

    private TextView mTextView;

    private ListView mListView;

    private ImageView mDeleteIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI();

        setListener();

    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mEdittext= (EditText) findViewById(R.id.activity_search_et);
        mTextView= (TextView) findViewById(R.id.activity_search_tv);
        mListView= (ListView) findViewById(R.id.activity_search_lv);
        mDeleteIv= (ImageView) findViewById(R.id.activity_search_iv_delete);
    }

    /**
     * 设置点击事件
     */
    private void setListener() {
        mEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDeleteIv.setVisibility(View.VISIBLE);

                if(isNumeric(s.toString())){
                    mSearchList= new ConstactsInfoTableHelper(SearchActivity.this).searchByTel(s.toString());
                    mListView.setAdapter(new DepartmentDetailAdapter(SearchActivity.this,mSearchList));
                }else{
                    getAutoCompleteData(s.toString());
                }

                if("".equals(s.toString())){
                    mDeleteIv.setVisibility(View.GONE);
                    mSearchList.clear();
                    mListView.setAdapter(new DepartmentDetailAdapter(SearchActivity.this, mSearchList));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdittext.setText("");
                mDeleteIv.setVisibility(View.GONE);
            }
        });
    }


    /** 搜索产生的数据 */
    private List<DepartmentDetailEntity> mSearchList;
    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {

        String url= Tools.jointIpAddress()
                + NetConstants.CONTACTS_SEARCH_PORT
                + "?"
                + NetConstants.CONTACTS_SEARCH_PARAM
                + "="
                + text;

        MyJsonObjectRequest myJsonObjectRequest=new MyJsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            if(jsonObject.getBoolean("success")){
                                int dataSize=jsonObject.getJSONArray("empList").length();
                                Log.e("Search1", dataSize + "");
                                JSONArray array=jsonObject.getJSONArray("empList");
                                Log.e("Search2",array.toString());
                                if (mSearchList == null) {
                                    //初始化
                                    mSearchList = new ArrayList<>(dataSize);
                                    Log.e("Search3",mSearchList.toString());
                                } else {
                                    // 根据text 获取auto data
                                    mSearchList.clear();
                                    Log.e("Search4", mSearchList.toString());
                                }

                                for (int i = 0; i <dataSize; i++) {
                                    DepartmentDetailEntity entity=new DepartmentDetailEntity();
                                    entity.setEmpname(array.getJSONObject(i).getString("empname"));
                                    entity.setSex(array.getJSONObject(i).getString("sex"));
                                    entity.setMobileno(array.getJSONObject(i).getString("mobileno"));
                                    entity.setOrgname(array.getJSONObject(i).getString("orgname"));
                                    entity.setPosiname(array.getJSONObject(i).getString("posiname"));
                                    entity.setOtel(array.getJSONObject(i).getString("otel"));
                                    entity.setOemail(array.getJSONObject(i).getString("oemail"));
                                    entity.setEmpid(array.getJSONObject(i).getInt("empid"));
                                    entity.setHeadimgUrl(array.getJSONObject(i).getString("headimg"));
                                    mSearchList.add(entity);
                                }

                                mListView.setAdapter(new DepartmentDetailAdapter(SearchActivity.this,mSearchList));

                            }else{
                                Toast.makeText(SearchActivity.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SearchActivity.this,"网络请求失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });

        VolleyHelper.getInstance(SearchActivity.this).addRequestTask(myJsonObjectRequest, "");

    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
