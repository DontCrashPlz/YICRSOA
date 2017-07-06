package com.hnsi.zheng.hnti_erp_app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hnsi.zheng.hnti_erp_app.beans.FileEntity;


/**
 * 附件信息表数据库助手类
 * Created by Zheng on 2017年1月9日15:30:16.
 */
public class FilesInfoTableHelper {

    private MYSQLiteOpenHelper mySQLiteOpenHelper=null;

    public FilesInfoTableHelper(Context context){
        mySQLiteOpenHelper=new MYSQLiteOpenHelper(context);
    }

    /**
     * 查询附件是否已下载
     * @param fileId
     * @return true=存在
     */
    public boolean queryFileInfo(String fileId){

        if (fileId==null
                ||"".equals(fileId)
                ||"null".equals(fileId)){
            return false;
        }

        SQLiteDatabase readableDB = mySQLiteOpenHelper.getReadableDatabase();

        Cursor cursor = readableDB.query(MYSQLiteOpenHelper.DB_TABLE_FILES_INFO,
                null, MYSQLiteOpenHelper.TableFilesInfo.FILEID +" = \'"+fileId+"\'", null, null, null, null);
        int n = cursor.getCount();
        cursor.close();
        readableDB.close();

        return  n > 0;
    }

    /**
     * 插入一条附件信息
     * @param entity
     * @return true=成功
     */
    public boolean insertFileInfo(FileEntity entity){
        if (entity==null){
            return false;
        }
        long result=0;
        SQLiteDatabase writableDB = mySQLiteOpenHelper.getWritableDatabase();

        Cursor cursor = writableDB.query(MYSQLiteOpenHelper.DB_TABLE_FILES_INFO,
                null, MYSQLiteOpenHelper.TableFilesInfo.FILEID+" = \'"+entity.getFileId()+"\'", null, null, null, null);
        int n = cursor.getCount();
        Log.e("insertN", "" + n);
        cursor.close();
        if (!(n > 0)){
            ContentValues values = new ContentValues();
            values.put(MYSQLiteOpenHelper.TableFilesInfo.FILENAME,entity.getFileName());
            values.put(MYSQLiteOpenHelper.TableFilesInfo.UPLOADTIME,entity.getFileUploadTime());
            values.put(MYSQLiteOpenHelper.TableFilesInfo.DOWNLOADURL, entity.getFilePath());
            values.put(MYSQLiteOpenHelper.TableFilesInfo.FILESIZE, entity.getFileSize());
            values.put(MYSQLiteOpenHelper.TableFilesInfo.FILEID, entity.getFileId());
            result = writableDB.insert(MYSQLiteOpenHelper.DB_TABLE_FILES_INFO, null, values);
            Log.e("insertR",""+result);
            writableDB.close();
        }
        return  result > 0;
    }

    /**
     * 删除一条附件信息
     * @param fileId
     * @return true=成功
     */
    public boolean deleteFileInfo(String fileId){
        if (fileId==null
                ||"".equals(fileId)
                ||"null".equals(fileId)){
            return false;
        }
        int result=0;
        SQLiteDatabase writableDB = mySQLiteOpenHelper.getWritableDatabase();

        Cursor cursor = writableDB.query(MYSQLiteOpenHelper.DB_TABLE_FILES_INFO,
                null, MYSQLiteOpenHelper.TableFilesInfo.FILEID+" = \'"+fileId+"\'", null, null, null, null);
        int n = cursor.getCount();
        cursor.close();
        if (n > 0){
            result = writableDB.delete(MYSQLiteOpenHelper.DB_TABLE_FILES_INFO,
                    MYSQLiteOpenHelper.TableFilesInfo.FILEID + " = \'"+fileId+"\'", null);
            writableDB.close();
        }
        return  result > 0;
    }

    /**
     * 清空附件表中的数据
     * @return 删掉纪录的条数
     */
    public int deleteAllFiles(){
        int result;

        SQLiteDatabase writableDB = mySQLiteOpenHelper.getWritableDatabase();

        result=writableDB.delete(MYSQLiteOpenHelper.DB_TABLE_FILES_INFO,null,null);

        writableDB.close();

        return  result;
    }

}
