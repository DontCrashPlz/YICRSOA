package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.activities.HtmlInfoActivity;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.shareclass.HashMap2MatterParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件详情数据
 * Created by Zheng on 2016/6/27.
 */
@Deprecated
public class MatterInfoListAdapter extends BaseAdapter {

    private Context mContext;
    private List mData;
    private LayoutInflater inflater;

    public MatterInfoListAdapter(Context context,List data){
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
            if("base".equals(object.getString("groupKey"))&&("text".equals(object.getString("type"))
                    |"textarea".equals(object.getString("type"))
                    |"date".equals(object.getString("type"))
                    |"int".equals(object.getString("type"))
                    |"FLOAT".equals(object.getString("type")))){
                View mView=inflater.inflate(R.layout.item_base_text_textarea_date_int,parent,false);
                TextView mNameTv= (TextView) mView.findViewById(R.id.item_base_name);
                TextView mValueTv= (TextView) mView.findViewById(R.id.item_base_value);
                mNameTv.setText(object.getString("label"));
                mValueTv.setText(object.getString("value"));

                //设置审批提交参数中的数据
                String key=object.getString("key").replace(".","/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                return mView;
            }else if("audit".equals(object.getString("groupKey"))&&"textarea".equals(object.getString("type"))){
                View mView=inflater.inflate(R.layout.item_audit_edittext, parent, false);

                //设置审批提交参数中的数据
                final String key=object.getString("key").replace(".", "/");
                String value="";
                if(object.getString("value")!=null){//如果value为null，参数传空字符串“”
                    value=object.getString("value");
                }
                HashMap2MatterParam.getInstance().putEle(key,value);

                EditText editText= (EditText) mView.findViewById(R.id.item_audit_et_suggest);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        HashMap2MatterParam.getInstance().putEle(key, s.toString());
                    }
                });

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
                    final TextView mButtonTv= (TextView) mView.findViewById(R.id.item_audit_button);
                    mNameTv.setText(object.getString("label"));
                    mButtonTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                JSONArray dataArray=object.getJSONArray("listData");
                                ArrayList<String>keyList=new ArrayList<String>();
                                final ArrayList<String>valueList=new ArrayList<String>();
                                for(int i=0;i<dataArray.length();i++){
                                    JSONObject obj=dataArray.getJSONObject(i);
                                    keyList.add(obj.getString("label"));
                                    valueList.add(obj.getString("value"));
                                }
                                final String key=object.getString("key").replace(".", "/");
                                final CharSequence[]items = new CharSequence[keyList.size()];
                                AlertDialog.Builder builder=new AlertDialog.Builder(mContext)
                                        .setTitle(object.getString("label"))
                                        .setItems(keyList.toArray(items), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mButtonTv.setText(items[which]);

                                                //设置审批提交参数中的数据
                                                String value=" ";
                                                if(valueList.get(which)!=null){//如果value为null，参数传空字符串“”
                                                    value=valueList.get(which);
                                                }
                                                HashMap2MatterParam.getInstance().putEle(key, value);
                                                Toast.makeText(mContext,value,Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                builder.create();
                                builder.show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return mView;
                }else{
                    JSONArray array=object.getJSONArray("listData");
                    Map<String,String>dataMap=new HashMap<>();
                    for(int i=0;i<array.length();i++){
                        dataMap.put(array.getJSONObject(i).getString("value"),array.getJSONObject(i).getString("label"));
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
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new View(mContext);
    }
}
