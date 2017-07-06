package com.hnsi.zheng.hnti_erp_app.beans;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hnsi.zheng.hnti_erp_app.app.MyApplication;

public class FormImage {
    //参数的名称
    private String mName ;
    //文件名
    private String mFileName ;
    //文件的 mime，需要根据文档查询
    private String mMime ;
    //需要上传的图片资源，因为这里测试为了方便起见，直接把 bigmap 传进来，真正在项目中一般不会这般做，而是把图片的路径传过来，在这里对图片进行二进制转换
    private String filePath ;

    public FormImage(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        //参数名称固定，直接写死
        return "data" ;
    }

    public String getFileName() {
    //直接写死文件的名字，username.jpg
        return MyApplication.mUser.getEmpname()+".jpg";
    }
    //对图片进行二进制转换
    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        Bitmap mBitmap= BitmapFactory.decodeFile(filePath);
        mBitmap.compress(Bitmap.CompressFormat.JPEG,80,bos) ;
        return bos.toByteArray();
    }
    //因为我知道是 png 文件，所以直接根据文档查的
    public String getMime() {
        return "image/jpg";
    }
}