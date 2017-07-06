package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.HtmlInfoActivity;
import com.hnsi.zheng.hnti_erp_app.activities.JingBanRenListActivity;
import com.hnsi.zheng.hnti_erp_app.activities.TableFilesActivity;
import com.hnsi.zheng.hnti_erp_app.activities.UnFinishedMatterActivity;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.shareclass.HashMap2MatterParam;
import com.hnsi.zheng.hnti_erp_app.utils.SharedPrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件详情数据Adapter(优化版,用来解决groupKey不为base和audit时无法显示视图的问题)
 * Created by Zheng on 2016/6/27.
 */
public class MatterInfoListAdapter2 extends BaseAdapter {

    private Context mContext;
    private List mData;
    private LayoutInflater inflater;

    public MatterInfoListAdapter2(Context context, List data){
        mContext=context;
        mData=data;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject object= (JSONObject) mData.get(position);
        try {
            if("text".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                mNameTv.setText(object.getString("label"));
                if("null".equals(object.getString("value"))){
                    mValueTv.setText("无");
                }else{
                    mValueTv.setText(object.getString("value"));
                }

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                return mView;
            }else if("textarea".equals(object.getString("type"))){
                if("audit".equals(object.getString("groupKey"))){
                    View mView=inflater.inflate(R.layout.item_audit_edittext, parent, false);

                    //设置审批提交参数中的数据
                    final String key = object.getString("key").replace(".", "/");
//                    String value="";
//                    if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
//                        value=object.getString("value");
//                    }

                    EditText editText= (EditText) mView.findViewById(R.id.item_audit_et_suggest);

                    if("wfParam/content".equals(key)){
                        String content="" + HashMap2MatterParam.getInstance().get().get("wfParam/content");
                        if("nul".equals(content)||"null".equals(content)){
                            editText.setText("");
                        }else{
                            editText.setText(content);
                        }
                    }

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if("".equals(s.toString())){
                                HashMap2MatterParam.getInstance().putEle(key, "nul");
                            }else{
                                HashMap2MatterParam.getInstance().putEle(key, s.toString());
                            }
                        }
                    });

                    return mView;
                }else{
                    View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                    TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                    TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                    mNameTv.setText(object.getString("label"));

                    if("null".equals(object.getString("value"))){
                        mValueTv.setText("无");
                    }else{
                        mValueTv.setText(object.getString("value"));
                    }

                    //设置审批提交参数中的数据
                    String key=object.getString("key").replace(".","/");
                    String value="";
                    if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                        value=object.getString("value");
                    }
                    HashMap2MatterParam.getInstance().putEle(key,value);

                    return mView;
                }
            }else if("date".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                mNameTv.setText(object.getString("label"));

                if("null".equals(object.getString("value"))){
                    mValueTv.setText("无");
                }else{
                    mValueTv.setText(object.getString("value"));
                }

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                return mView;
            }else if("int".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                mNameTv.setText(object.getString("label"));

                if("null".equals(object.getString("value"))){
                    mValueTv.setText("无");
                }else{
                    mValueTv.setText(object.getString("value"));
                }

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                return mView;
            }else if("FLOAT".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                mNameTv.setText(object.getString("label"));

                if("null".equals(object.getString("value"))){
                    mValueTv.setText("无");
                }else{
                    mValueTv.setText(object.getString("value"));
                }

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                return mView;
            }else if("tableView".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_tableview_list,parent,false);
                ListView mTableList= (ListView) mView.findViewById(R.id.item_tableview_lv);
                List<LinkedHashMap>dataList=new ArrayList<>();
                JSONArray array=object.getJSONArray("tableData");
                JSONArray titleArray=object.getJSONArray("tableTitle");
                for(int i=0;i<array.length();i++){
                    LinkedHashMap<String,String>dataMap=new LinkedHashMap<>();
                    JSONArray tableArray=array.getJSONArray(i);
                    int tableSize=tableArray.length();
                    for(int j=0;j<tableSize;j++){
                        dataMap.put(titleArray.getString(j),tableArray.getString(j));
                    }
                    dataList.add(dataMap);
                }
                mTableList.setAdapter(new TableViewAdapter(mContext,dataList,object.getString("label")));

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".", "/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                return mView;
            }else if("html".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_base_html,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                mNameTv.setText(object.getString("label"));
                RelativeLayout mPanelRly= (RelativeLayout) mView.findViewById(R.id.item_rly_html_panel);
                mPanelRly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String contentStr=object.getString("value");
                            Intent intent=new Intent();
                            intent.putExtra(AppConstants.HTML_CONTENT,contentStr);
                            intent.setClass(mContext, HtmlInfoActivity.class);
                            mContext.startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".", "/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key, value);

                return mView;
            }else if("list".equals(object.getString("type"))){
                if("audit".equals(object.getString("groupKey"))){
                    View mView=inflater.inflate(R.layout.item_audit_button,parent,false);
                    TextView mNameTv= (TextView) mView.findViewById(R.id.item_audit_name);
                    TextView mButtonTv= (TextView) mView.findViewById(R.id.item_audit_button);
                    mButtonTv.setText("请点击选择");

                    final String key=object.getString("key").replace(".", "/");
                    String value=object.getString("value");
                    JSONArray dataArray=object.getJSONArray("listData");
                    //所有列表数据的label值的集合
                    final ArrayList<String>labelList=new ArrayList<>();
                    //所有列表数据的label值的集合
                    final ArrayList<String>valueList=new ArrayList<>();
                    //所有列表数据的Map集合
                    final HashMap<String,String> dataMap=new HashMap<>();
                    //给数据集合添加数据
                    for(int i=0;i<dataArray.length();i++){
                        JSONObject obj=dataArray.getJSONObject(i);
                        labelList.add(obj.getString("label"));
                        valueList.add(obj.getString("value"));
                        dataMap.put(obj.getString("value"),obj.getString("label"));
                    }
                    final CharSequence[]items = new CharSequence[labelList.size()];

                    if("nul".equals(HashMap2MatterParam.getInstance().get().get(key))
                            ||"null".equals(HashMap2MatterParam.getInstance().get().get(key))
                            ||"".equals(HashMap2MatterParam.getInstance().get().get(key))
                            ||!HashMap2MatterParam.getInstance().get().containsKey(key)){
                        if(!"null".equals(value)){
                            HashMap2MatterParam.getInstance().putEle(key, value);
                        }
                    }

                    if(HashMap2MatterParam.getInstance().get().containsKey(key)){
                        String content= (String) HashMap2MatterParam.getInstance().get().get(key);
                        if(dataMap.containsKey(content)){
                            mButtonTv.setText(dataMap.get(content));
                        }
                    }

                    mNameTv.setText(object.getString("label"));
                    mButtonTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {

                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                                        .setTitle(object.getString("label"))
                                        .setItems(labelList.toArray(items), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //设置审批提交参数中的数据
                                                String value = "";
                                                if (valueList.get(which) != null) {//如果value为null，参数传空字符串“”
                                                    value = valueList.get(which);
                                                }
                                                HashMap2MatterParam.getInstance().putEle(key, value);

                                                if("wfParam/changyong".equals(key)){
                                                    HashMap2MatterParam.getInstance().putEle("wfParam/content", value);
                                                }

                                                notifyDataSetChanged();
                                            }
                                        });
                                builder.create();
                                builder.show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    //判断此项是否是switch的关联项
                    if(((UnFinishedMatterActivity) mContext).getConnectKey().equals(key)){
                        if(((UnFinishedMatterActivity) mContext).getIsConnect().equals("0")){//switch当前状态为off
                            mNameTv.setTextColor(Color.rgb(177, 177, 177));
                            mButtonTv.setBackgroundColor(Color.rgb(177, 177, 177));
                            mButtonTv.setClickable(false);
                        }else if(((UnFinishedMatterActivity) mContext).getIsConnect().equals("1")){//switch当前状态为on
                            if(HashMap2MatterParam.getInstance().get().containsKey(key)){
                                mButtonTv.setText(dataMap.get(HashMap2MatterParam.getInstance().get().get(key)));
                                HashMap2MatterParam.getInstance().putEle(key, (String) HashMap2MatterParam.getInstance().get().get(key));
                            }else{
                                mButtonTv.setText(dataMap.get(object.getString("value")));
                                HashMap2MatterParam.getInstance().putEle(key, object.getString("value"));
                            }
                        }
                    }

                    //判断此项是否是互斥开关的第一个关联项
                    if(((UnFinishedMatterActivity) mContext).getFristMutualKey().equals(key)){
                        if("0".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                            mButtonTv.setText(dataMap.get(HashMap2MatterParam.getInstance().get().get(key)));
                            HashMap2MatterParam.getInstance().putEle(key, (String) HashMap2MatterParam.getInstance().get().get(key));
                        }else if("1".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                            mNameTv.setTextColor(Color.rgb(177, 177, 177));
                            mButtonTv.setBackgroundColor(Color.rgb(177, 177, 177));
                            mButtonTv.setClickable(false);
                        }
                    }

                    //判断此项是否是互斥开关的第二个关联项
                    if(((UnFinishedMatterActivity) mContext).getSecondMutualKey().equals(key)){
                        if("0".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                            mNameTv.setTextColor(Color.rgb(177, 177, 177));
                            mButtonTv.setBackgroundColor(Color.rgb(177, 177, 177));
                            mButtonTv.setClickable(false);
                        }else if("1".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                            mButtonTv.setText(dataMap.get(HashMap2MatterParam.getInstance().get().get(key)));
                            HashMap2MatterParam.getInstance().putEle(key, (String) HashMap2MatterParam.getInstance().get().get(key));
                        }
                    }

                    return mView;
                }else{
                    JSONArray array=object.getJSONArray("listData");
                    Map<String,String>dataMap=new HashMap<>();
                    for(int i=0;i<array.length();i++){
                        dataMap.put(array.getJSONObject(i).
                                getString("value"),array.getJSONObject(i).getString("label"));
                    }
                    View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                    TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                    TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                    mNameTv.setText(object.getString("label"));
                    mValueTv.setText(dataMap.get(object.getString("value")));

                    //设置审批提交参数中的数据
                    String key=object.getString("key").replace(".", "/");
                    String value="";
                    if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                        value=object.getString("value");
                    }
                    HashMap2MatterParam.getInstance().putEle(key, value);

                    return mView;
                }
            }else if("hidden".equals(object.getString("type"))){
                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);
            }else if("datetime".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                mNameTv.setText(object.getString("label"));

                if("null".equals(object.getString("value"))){
                    mValueTv.setText("无");
                }else{
                    mValueTv.setText(object.getString("value"));
                }

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                return mView;
            }else if("alllist".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_audit_button,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_audit_name);
                final TextView mButtonTv= (TextView) mView.findViewById(R.id.item_audit_button);
                mNameTv.setText(object.getString("label"));

                //提交参数key值
                String key=object.getString("key").replace(".", "/");
                //默认value值
                String value=object.getString("value");
                //默认value值对应的名字
                String defaultName=object.getString("prompt");

                //从Activity中拿到经办人姓名字符串，如果是有效字符串，从控件中显示出来
                String mJingBanRenName=((UnFinishedMatterActivity) mContext).getJingBanRenStr();
                if("".equals(mJingBanRenName)){
                    if(!("null".equals(value))&&!("null".equals(defaultName))){
                        ((UnFinishedMatterActivity) mContext).setJingBanRenStr(defaultName);
                        ((UnFinishedMatterActivity) mContext).setJingBanRenId(value);
                        HashMap2MatterParam.getInstance().putEle(key, value);
                        mButtonTv.setText(defaultName);
                        Log.e("allList","1");
                    }else{
                        mButtonTv.setText("请点击选择");
                    }
                }else{
                    mButtonTv.setText(mJingBanRenName);
                    HashMap2MatterParam.getInstance().putEle(key, ((UnFinishedMatterActivity) mContext).getJingBanRenId());
                }

                mButtonTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, JingBanRenListActivity.class);
//                        SharedPrefUtils.put(mContext, "ENTER_JingBanRenList", true);
//                        ((UnFinishedMatterActivity) mContext).setJingBanRenTv(mButtonTv);
                        ((UnFinishedMatterActivity) mContext).getThisActivity().startActivityForResult(intent, 110);

                    }
                });

                //判断此项是否是互斥开关的第一个关联项
                if(((UnFinishedMatterActivity) mContext).getFristMutualKey().equals(key)){
                    if("0".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                        mButtonTv.setText(((UnFinishedMatterActivity) mContext).getJingBanRenStr());
                        HashMap2MatterParam.getInstance().putEle(key, ((UnFinishedMatterActivity) mContext).getJingBanRenId());
                    }else if("1".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                        mNameTv.setTextColor(Color.rgb(177, 177, 177));
                        mButtonTv.setBackgroundColor(Color.rgb(177, 177, 177));
                        mButtonTv.setClickable(false);
                    }
                }

                //判断此项是否是互斥开关的第二个关联项
                if(((UnFinishedMatterActivity) mContext).getSecondMutualKey().equals(key)){
                    if("0".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                        mNameTv.setTextColor(Color.rgb(177, 177, 177));
                        mButtonTv.setBackgroundColor(Color.rgb(177, 177, 177));
                        mButtonTv.setClickable(false);
                    }else if("1".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                        mButtonTv.setText(((UnFinishedMatterActivity) mContext).getJingBanRenStr());
                        HashMap2MatterParam.getInstance().putEle(key, ((UnFinishedMatterActivity) mContext).getJingBanRenId());
                    }
                }

                return mView;
            }else if("switch".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_audit_switch,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_audit_name);
                ToggleButton mSwitchTb= (ToggleButton) mView.findViewById(R.id.item_audit_switch);
                mNameTv.setText(object.getString("label"));

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";

                //如果Activity中没有存放switch的value，把默认的value值存进去
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                    if("".equals(((UnFinishedMatterActivity) mContext).getIsConnect())){
                        ((UnFinishedMatterActivity) mContext).setIsConnect(value);
                    }
                }

                //switch开关控制的控件的key
                String connectKey=object.getString("prompt").replace(".","/");
                ((UnFinishedMatterActivity) mContext).setConnectKey(connectKey);
                //Activity中存放的value值
                String actValue=((UnFinishedMatterActivity) mContext).getIsConnect();
                if("0".equals(actValue)){
                    mSwitchTb.setChecked(false);
                    HashMap2MatterParam.getInstance().putEle(key, actValue);
                }else if("1".equals(actValue)){
                    mSwitchTb.setChecked(true);
                    HashMap2MatterParam.getInstance().putEle(key, actValue);
                }

                mSwitchTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            ((UnFinishedMatterActivity) mContext).setIsConnect("1");
                            notifyDataSetChanged();
                        }else{
                            ((UnFinishedMatterActivity) mContext).setIsConnect("0");
                            notifyDataSetChanged();
                        }
                    }
                });

                return mView;
            }else if("switchmutex".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_audit_switch,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_audit_name);
                ToggleButton mSwitchTb= (ToggleButton) mView.findViewById(R.id.item_audit_switch);
                mNameTv.setText(object.getString("label"));

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value=object.getString("value");

                //如果Activity中没有纪录互斥开关的当前状态，放入默认值
                if("".equals(((UnFinishedMatterActivity) mContext).getMutualStatus())){
                    ((UnFinishedMatterActivity) mContext).setMutualStatus(value);
                }

                //互斥开关控制的两个控件的key
                String connectKey=object.getString("prompt").replace(".","/");
                //如果Activity中未存入互斥开关控制的两个控件的key值，就给它们传值
                if("".equals(((UnFinishedMatterActivity) mContext).getFristMutualKey())
                        ||"".equals(((UnFinishedMatterActivity) mContext).getSecondMutualKey())){
                    String[]keys=connectKey.split("\\|");
                    Log.e("mutualArray", ""+keys.length);
                    ((UnFinishedMatterActivity) mContext).setFristMutualKey(keys[0]);
                    ((UnFinishedMatterActivity) mContext).setSecondMutualKey(keys[1]);
                }

                //提交参数Map中存放的value值
                String actValue=((UnFinishedMatterActivity) mContext).getMutualStatus();
                if("0".equals(actValue)){
                    mSwitchTb.setChecked(false);
                    HashMap2MatterParam.getInstance().putEle(key, "0");
                }else if("1".equals(actValue)){
                    mSwitchTb.setChecked(true);
                    HashMap2MatterParam.getInstance().putEle(key, "1");
                }

                mSwitchTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            ((UnFinishedMatterActivity) mContext).setMutualStatus("1");
                            notifyDataSetChanged();
                        }else{
                            ((UnFinishedMatterActivity) mContext).setMutualStatus("0");
                            notifyDataSetChanged();
                        }
                    }
                });
                return mView;
            }else if("tableFiles".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_base_tablefiles,parent,false);
                RelativeLayout mPanelRly= (RelativeLayout) mView.findViewById(R.id.item_rly_html_panel);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);

                mPanelRly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, TableFilesActivity.class);
                        intent.putExtra("filesInfo", object.toString());
                        mContext.startActivity(intent);
                    }
                });
                mNameTv.setText(object.getString("label"));

                return mView;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new View(mContext);
    }
}
