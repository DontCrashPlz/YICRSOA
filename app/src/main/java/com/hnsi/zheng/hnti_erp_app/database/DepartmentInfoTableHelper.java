package com.hnsi.zheng.hnti_erp_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hnsi.zheng.hnti_erp_app.beans.DepartmentDetailEntity;
import com.hnsi.zheng.hnti_erp_app.beans.DepartmentEntity2;
import com.hnsi.zheng.hnti_erp_app.newcontacts.ItemData;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人信息表数据库助手类
 * Created by Zheng on 2016/9/6.
 */
public class DepartmentInfoTableHelper {

    private MYSQLiteOpenHelper mySQLiteOpenHelper=null;

    public DepartmentInfoTableHelper(Context context){
        mySQLiteOpenHelper=new MYSQLiteOpenHelper(context);
    }

    /**
     * 插入一批数据
     * @param departments
     * @return 成功的条目数
     */
    public synchronized long insertAll(List<DepartmentEntity2> departments){
        if (departments==null || departments.size() == 0){
            return 0;
        }
        SQLiteDatabase writableDB = mySQLiteOpenHelper.getWritableDatabase();
        long result = 0;
        // 开启事务
        writableDB.beginTransaction();
        for (DepartmentEntity2 department : departments) {

            // selection 首先判断是否存在
            Cursor cursor = writableDB.query(MYSQLiteOpenHelper.DB_TABLE_DEPARTMENT_INFO,
                    null, MYSQLiteOpenHelper.TableDepartmentInfo.ORGID+" = "+department.getOrgid(), null, null, null, null);
            int n = cursor.getCount();
            cursor.close();
            if (n > 0){
                continue;
            }

            final ContentValues values = new ContentValues();
            values.put(MYSQLiteOpenHelper.TableDepartmentInfo.ORGID,department.getOrgid());
            values.put(MYSQLiteOpenHelper.TableDepartmentInfo.ORGNAME,department.getOrgname());
            values.put(MYSQLiteOpenHelper.TableDepartmentInfo.PARENTORGID,department.getParentorgid());
            values.put(MYSQLiteOpenHelper.TableDepartmentInfo.CHILDRENNUM,department.getChildrenNum());
            result += writableDB.insert(MYSQLiteOpenHelper.DB_TABLE_DEPARTMENT_INFO, null, values);
        }
        // 设置事务成功
        writableDB.setTransactionSuccessful();
        // 关闭事务
        writableDB.endTransaction();
        writableDB.close();
        return result;
    }

    /**
     * 删除所有部门数据
     * @return
     */
    public synchronized boolean deleteAllDepartments(){
        int result = 0;
        SQLiteDatabase writableDB = mySQLiteOpenHelper.getWritableDatabase();

        result = writableDB.delete(MYSQLiteOpenHelper.DB_TABLE_DEPARTMENT_INFO,
                MYSQLiteOpenHelper.TableDepartmentInfo.ORGID + " > 0 ", null);

        writableDB.close();
        return result > 0;
    }

    /**
     * 获取所有一级部门
     */
    public ArrayList<ItemData> queryAllFirstDepartment(){
        SQLiteDatabase readableDB = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("select * from " + MYSQLiteOpenHelper.DB_TABLE_DEPARTMENT_INFO + " where _parentorgid = 1", null);

        ArrayList<ItemData> mDepartmentList = new ArrayList<>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                ItemData entity = createDepartmentEntity(cursor);
                if (entity != null){
                    mDepartmentList.add(entity);
                }
                cursor.moveToNext();
            }
        }

        cursor.close();
        readableDB.close();

        return mDepartmentList;
    }

    /**
     * 生成部门对象
     * @param cursor
     * @return
     */
    public ItemData createDepartmentEntity(Cursor cursor){
        ItemData entity = null;
        if (cursor != null){
            entity = new ItemData();
            try {
                if(1==cursor.getInt(cursor.getColumnIndex(MYSQLiteOpenHelper.TableDepartmentInfo.PARENTORGID))){
                    entity.setType(ItemData.ITEM_TYPE_PARENT);
                }else{
                    entity.setType(ItemData.ITEM_TYPE_CHILD_PARENT);
                }
//                entity.setType(ItemData.ITEM_TYPE_PARENT);
                entity.setText(cursor.getString(cursor.getColumnIndex(MYSQLiteOpenHelper.TableDepartmentInfo.ORGNAME)));
                entity.setText2(""+cursor.getInt(cursor.getColumnIndex(MYSQLiteOpenHelper.TableDepartmentInfo.CHILDRENNUM)));
                entity.setOrgid(cursor.getInt(cursor.getColumnIndex(MYSQLiteOpenHelper.TableDepartmentInfo.ORGID)));
                entity.setParentorgid(cursor.getInt(cursor.getColumnIndex(MYSQLiteOpenHelper.TableDepartmentInfo.PARENTORGID)));
            } catch (Exception e) {
            }
        }
        return entity;
    }

    /**
     * 根据orgid获取子部门
     */
    public ArrayList<ItemData> queryChildDepartment(int orgid){
        SQLiteDatabase readableDB = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = readableDB.rawQuery("select * from " + MYSQLiteOpenHelper.DB_TABLE_DEPARTMENT_INFO + " where _parentorgid = "+orgid, null);

        ArrayList<ItemData> mDepartmentList = new ArrayList<>();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                final ItemData entity = createDepartmentEntity(cursor);
                if (entity != null){
                    mDepartmentList.add(entity);
                }
                cursor.moveToNext();
            }
        }

        cursor.close();
        readableDB.close();

        return mDepartmentList;
    }

}
