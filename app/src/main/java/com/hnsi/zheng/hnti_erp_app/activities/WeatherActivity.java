package com.hnsi.zheng.hnti_erp_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.http.MyJsonObjectRequest;
import com.hnsi.zheng.hnti_erp_app.http.VolleyHelper;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.BaseProgressDialog;
import com.hnsi.zheng.hnti_erp_app.widgets.progressDialog.ProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 天气界面
 * Created by Zheng on 2016/8/29.
 */
@Deprecated
public class WeatherActivity extends MyBaseActivity{

    private ImageButton mBackBtn;//返回按钮
    private TextView mCityListTv;//城市列表按钮
    private ImageView mBackgroundIv;//天气背景
    private TextView mRecentlyTempuratureTv;//实时气温
    private ImageView mWiconIv;//实时天气图标
    private TextView mDateTv;//日期
    private TextView mWhatDayTv;//周几
    private TextView mClimateTv;//实时气候
    private TextView mWindTv;//实时风况
    private TextView mValueOfPmTv;//PM2.5值
    private TextView mEvaluateTv;//PM2.5评价
    private ImageView mTodayIconIv;//今日天气图标
    private TextView mTodayClimateTv;//今日气候
    private TextView mTodayTempuratureTv;//今日气温
    private ImageView mTomorrowIconIv;//明日天气图标
    private TextView mTomorrowClimateTv;//明日气候
    private TextView mTomorrowTempuratureTv;//明日气温
    private ImageView mAfterTomorrowIconIv;//后天天气图标
    private TextView mAfterTomorrowClimateTv;//后天气候
    private TextView mAfterTomorrowTempuratureTv;//后天气温

    private BaseProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        dialog=new ProgressDialog(this);
        dialog.setLabel("加载中...");

        initUI();

        loadWeatherData();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mBackBtn= (ImageButton) findViewById(R.id.titlebar_ibtn_back);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mCityListTv= (TextView) findViewById(R.id.titlebar_tv_city_list);
        mCityListTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(WeatherActivity.this,CityListActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        mBackgroundIv= (ImageView) findViewById(R.id.weather_iv_background);
        mRecentlyTempuratureTv= (TextView) findViewById(R.id.weather_tv_recently);
        mWiconIv= (ImageView) findViewById(R.id.weather_iv_wicon);
        mDateTv= (TextView) findViewById(R.id.weather_tv_date);
        mWhatDayTv= (TextView) findViewById(R.id.weather_tv_whatday);
        mClimateTv= (TextView) findViewById(R.id.weather_tv_climate);
        mWindTv= (TextView) findViewById(R.id.weather_tv_wind);
        mValueOfPmTv= (TextView) findViewById(R.id.weather_tv_value_of_pm);
        mEvaluateTv= (TextView) findViewById(R.id.weather_tv_evaluate_of_pm);
        mTodayIconIv= (ImageView) findViewById(R.id.weather_iv_today_icon);
        mTodayClimateTv= (TextView) findViewById(R.id.weather_tv_today_climate);
        mTodayTempuratureTv= (TextView) findViewById(R.id.weather_tv_today_tempurature);
        mTomorrowIconIv= (ImageView) findViewById(R.id.weather_iv_tomorrow_icon);
        mTomorrowClimateTv= (TextView) findViewById(R.id.weather_tv_tomorrow_climate);
        mTomorrowTempuratureTv= (TextView) findViewById(R.id.weather_tv_tomorrow_tempurature);
        mAfterTomorrowIconIv= (ImageView) findViewById(R.id.weather_iv_aftertomorrow_icon);
        mAfterTomorrowClimateTv= (TextView) findViewById(R.id.weather_tv_aftertomorrow_climate);
        mAfterTomorrowTempuratureTv= (TextView) findViewById(R.id.weather_tv_aftertomorrow_tempurature);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadWeatherData();
    }

    /**
     * 加载天气数据
     */
    private void loadWeatherData() {

        dialog.show();

        final String province= (String) SharedPrefUtils.get(WeatherActivity.this,"province","河南");
        final String city= (String) SharedPrefUtils.get(WeatherActivity.this,"city","郑州");
        String weatherUrl= Tools.buildWeatherUrl(province,city);
        Log.e("weatherUrl",weatherUrl);

        mCityListTv.setText(city);

        MyJsonObjectRequest request=new MyJsonObjectRequest(weatherUrl,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray weatherArray=jsonObject.getJSONArray(province+"|"+city);

                            //设置整体背景和今日天气数据
                            JSONObject todayWeather=weatherArray.getJSONObject(0);
                            String todayClimate=todayWeather.getString("climate");
                            if("晴".equals(todayClimate)){
                                mBackgroundIv.setImageResource(R.mipmap.weather_background_sunny);
                                mWiconIv.setImageResource(R.mipmap.weather_wicon_sunny);
                                mTodayIconIv.setImageResource(R.mipmap.weather_icon_sunny);
                            }else if("多云".equals(todayClimate)){
                                mBackgroundIv.setImageResource(R.mipmap.weather_background_cloudy);
                                mWiconIv.setImageResource(R.mipmap.weather_wicon_cloudy);
                                mTodayIconIv.setImageResource(R.mipmap.weather_icon_cloudy);
                            }else if("阴".equals(todayClimate)){
                                mBackgroundIv.setImageResource(R.mipmap.weather_background_overcast);
                                mWiconIv.setImageResource(R.mipmap.weather_wicon_overcast);
                                mTodayIconIv.setImageResource(R.mipmap.weather_icon_overcast);
                            }else if("小雨".equals(todayClimate)
                                    |"中雨".equals(todayClimate)
                                    |"大雨".equals(todayClimate)
                                    |"暴雨".equals(todayClimate)
                                    |"阵雨".equals(todayClimate)){
                                mBackgroundIv.setImageResource(R.mipmap.weather_background_rainy);
                                mWiconIv.setImageResource(R.mipmap.weather_wicon_rainy);
                                mTodayIconIv.setImageResource(R.mipmap.weather_icon_rainy);
                            }else if("雷阵雨".equals(todayClimate)){
                                mBackgroundIv.setImageResource(R.mipmap.weather_background_thunderstorm);
                                mWiconIv.setImageResource(R.mipmap.weather_wicon_thunderstorm);
                                mTodayIconIv.setImageResource(R.mipmap.weather_icon_thunderstorm);
                            }else if("小雪".equals(todayClimate)
                                    |"中雪".equals(todayClimate)
                                    |"大雪".equals(todayClimate)
                                    |"暴雪".equals(todayClimate)
                                    |"雨夹雪".equals(todayClimate)){
                                mBackgroundIv.setImageResource(R.mipmap.weather_background_snowy);
                                mWiconIv.setImageResource(R.mipmap.weather_wicon_snowy);
                                mTodayIconIv.setImageResource(R.mipmap.weather_icon_snowy);
                            }else if("沙尘暴".equals(todayClimate)){
                                mBackgroundIv.setImageResource(R.mipmap.weather_background_sandstorm);
                                mWiconIv.setImageResource(R.mipmap.weather_wicon_sandstorm);
                                mTodayIconIv.setImageResource(R.mipmap.weather_icon_sandstorm);
                            }
                            mRecentlyTempuratureTv.setText(""+jsonObject.getInt("rt_temperature"));
                            mDateTv.setText(jsonObject.getString("dt"));
                            mWhatDayTv.setText(todayWeather.getString("week"));
                            mClimateTv.setText(todayClimate);
                            mWindTv.setText(todayWeather.getString("wind"));
                            mValueOfPmTv.setText(jsonObject.getJSONObject("pm2d5").getString("pm2_5"));
                            int aqi=Integer.parseInt(jsonObject.getJSONObject("pm2d5").getString("aqi"));
                            if(0<=aqi&&aqi<=50){
                                mEvaluateTv.setText("优");
                            }else if(50<aqi&&aqi<=100){
                                mEvaluateTv.setText("良");
                            }else if(100<aqi&&aqi<=150){
                                mEvaluateTv.setText("轻度污染");
                            }else if(150<aqi&&aqi<=200){
                                mEvaluateTv.setText("中度污染");
                            }else if(200<aqi&&aqi<=300){
                                mEvaluateTv.setText("重度污染");
                            }else if(300<aqi){
                                mEvaluateTv.setText("严重污染");
                            }
                            mTodayClimateTv.setText(todayClimate);
                            mTodayTempuratureTv.setText(todayWeather.getString("temperature"));

                            //设置明天天气数据
                            JSONObject tomorrowWeather=weatherArray.getJSONObject(1);
                            String tomorrowClimate=tomorrowWeather.getString("climate");
                            if("晴".equals(tomorrowClimate)){
                                mTomorrowIconIv.setImageResource(R.mipmap.weather_icon_sunny);
                            }else if("多云".equals(tomorrowClimate)){
                                mTomorrowIconIv.setImageResource(R.mipmap.weather_icon_cloudy);
                            }else if("阴".equals(tomorrowClimate)){
                                mTomorrowIconIv.setImageResource(R.mipmap.weather_icon_overcast);
                            }else if("小雨".equals(tomorrowClimate)
                                    |"中雨".equals(tomorrowClimate)
                                    |"大雨".equals(tomorrowClimate)
                                    |"暴雨".equals(tomorrowClimate)
                                    |"阵雨".equals(tomorrowClimate)){
                                mTomorrowIconIv.setImageResource(R.mipmap.weather_icon_rainy);
                            }else if("雷阵雨".equals(tomorrowClimate)){
                                mTomorrowIconIv.setImageResource(R.mipmap.weather_icon_thunderstorm);
                            }else if("小雪".equals(tomorrowClimate)
                                    |"中雪".equals(tomorrowClimate)
                                    |"大雪".equals(tomorrowClimate)
                                    |"暴雪".equals(tomorrowClimate)
                                    |"雨夹雪".equals(tomorrowClimate)){
                                mTomorrowIconIv.setImageResource(R.mipmap.weather_icon_snowy);
                            }else if("沙尘暴".equals(tomorrowClimate)){
                                mTomorrowIconIv.setImageResource(R.mipmap.weather_icon_sandstorm);
                            }
                            mTomorrowClimateTv.setText(tomorrowClimate);
                            mTomorrowTempuratureTv.setText(tomorrowWeather.getString("temperature"));

                            //设置后天天气数据
                            JSONObject afterTomorrowWeather=weatherArray.getJSONObject(2);
                            String afterTomorrowClimate=afterTomorrowWeather.getString("climate");
                            if("晴".equals(afterTomorrowClimate)){
                                mAfterTomorrowIconIv.setImageResource(R.mipmap.weather_icon_sunny);
                            }else if("多云".equals(afterTomorrowClimate)){
                                mAfterTomorrowIconIv.setImageResource(R.mipmap.weather_icon_cloudy);
                            }else if("阴".equals(afterTomorrowClimate)){
                                mAfterTomorrowIconIv.setImageResource(R.mipmap.weather_icon_overcast);
                            }else if("小雨".equals(afterTomorrowClimate)
                                    |"中雨".equals(afterTomorrowClimate)
                                    |"大雨".equals(afterTomorrowClimate)
                                    |"暴雨".equals(afterTomorrowClimate)
                                    |"阵雨".equals(afterTomorrowClimate)){
                                mAfterTomorrowIconIv.setImageResource(R.mipmap.weather_icon_rainy);
                            }else if("雷阵雨".equals(afterTomorrowClimate)){
                                mAfterTomorrowIconIv.setImageResource(R.mipmap.weather_icon_thunderstorm);
                            }else if("小雪".equals(afterTomorrowClimate)
                                    |"中雪".equals(afterTomorrowClimate)
                                    |"大雪".equals(afterTomorrowClimate)
                                    |"暴雪".equals(afterTomorrowClimate)
                                    |"雨夹雪".equals(afterTomorrowClimate)){
                                mAfterTomorrowIconIv.setImageResource(R.mipmap.weather_icon_snowy);
                            }else if("沙尘暴".equals(afterTomorrowClimate)){
                                mAfterTomorrowIconIv.setImageResource(R.mipmap.weather_icon_sandstorm);
                            }
                            mAfterTomorrowClimateTv.setText(afterTomorrowClimate);
                            mAfterTomorrowTempuratureTv.setText(afterTomorrowWeather.getString("temperature"));

                            dialog.dismiss();
                        } catch (JSONException e) {
                            dialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        dialog.dismiss();
                        Toast.makeText(WeatherActivity.this,"天气服务器异常，请稍后重试",Toast.LENGTH_SHORT).show();
                    }
                });

        //设置网络请求超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));

        VolleyHelper.getInstance(WeatherActivity.this)
                .addRequestTask(request, weatherUrl);
    }
}
