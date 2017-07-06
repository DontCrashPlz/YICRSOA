package com.hnsi.zheng.hnti_erp_app.newcontacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.database.ConstactsInfoTableHelper;
import com.hnsi.zheng.hnti_erp_app.database.DepartmentInfoTableHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zheng on 2017/5/27.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<ItemData> mDataSet;
    private DepartmentInfoTableHelper departmentInfoTableHelper;
    private ConstactsInfoTableHelper constactsInfoTableHelper;

    public MyRecyclerAdapter(Context context){
        mContext=context;
        mDataSet=new ArrayList<>();
        departmentInfoTableHelper=new DepartmentInfoTableHelper(context);
        constactsInfoTableHelper=new ConstactsInfoTableHelper(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case ItemData.ITEM_TYPE_PARENT:
                view= LayoutInflater.from(mContext).inflate(R.layout.aaitem_contacts_department,parent,false);
                return new ParentViewHolder(view);
            case ItemData.ITEM_TYPE_CHILD_PARENT:
                view= LayoutInflater.from(mContext).inflate(R.layout.aaitem_contacts_department2,parent,false);
                return new ParentViewHolder(view);
            case ItemData.ITEM_TYPE_CHILD:
                view= LayoutInflater.from(mContext).inflate(R.layout.aaitem_department_detail,parent,false);
                return new ChildViewHolder(view);
            default:
                view= LayoutInflater.from(mContext).inflate(R.layout.aaitem_department_detail,parent,false);
                return new ChildViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        switch (mDataSet.get(position).getType()){
            case ItemData.ITEM_TYPE_PARENT:
                ParentViewHolder pHolder= (ParentViewHolder) holder;
                pHolder.bindData(mDataSet.get(position),position,listener);
                break;
            case ItemData.ITEM_TYPE_CHILD_PARENT:
                ParentViewHolder cpHolder= (ParentViewHolder) holder;
                cpHolder.bindData(mDataSet.get(position),position,listener);
                break;
            case ItemData.ITEM_TYPE_CHILD:
                ChildViewHolder cHolder= (ChildViewHolder) holder;
                cHolder.bindData(mDataSet.get(position),mContext);
                break;
            default:
                break;
        }
    }

    private ItemDataClickListener listener=new ItemDataClickListener() {
        @Override
        public void onExpandChildren(int position) {
            ItemData itemData=mDataSet.get(position);
            List<ItemData> list=departmentInfoTableHelper.queryChildDepartment(itemData.getOrgid());
            list.addAll(constactsInfoTableHelper.queryAllOrgidItemDatas(itemData.getOrgid()));
            if (list.size()>0){
                addAll(list,position+1);
                itemData.setChildren(list);
            }
        }

        @Override
        public void onHideChildren(int position) {
            ItemData itemData=mDataSet.get(position);
            List<ItemData>list=itemData.getChildren();
            if(list==null){
                return;
            }
            removeAll(position+1,list.size());
            itemData.setChildren(null);
        }
    };

    /**
     * 将集合中的数据添加到列表中
     * @param list
     * @param position
     */
    public void addAll(List<ItemData>list,int position){
        mDataSet.addAll(position,list);
        notifyItemRangeInserted(position,list.size());
    }

    /**
     * @param position
     * @param itemCount
     */
    private void removeAll(int position,int itemCount){
        for (int i=0;i<itemCount;i++){
            mDataSet.remove(position);
        }
        notifyItemRangeRemoved(position, itemCount);
    }

    public void clearAdapter(){
        int count=mDataSet.size();
        mDataSet.clear();
        notifyItemRangeRemoved(0, count);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).getType();
    }
}
