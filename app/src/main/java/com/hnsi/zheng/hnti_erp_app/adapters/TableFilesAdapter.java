package com.hnsi.zheng.hnti_erp_app.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hnsi.zheng.hnti_erp_app.R;
import com.hnsi.zheng.hnti_erp_app.app.AppConstants;
import com.hnsi.zheng.hnti_erp_app.app.MyApplication;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.FileEntity;
import com.hnsi.zheng.hnti_erp_app.database.FilesInfoTableHelper;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * tablefiles附件列表数据adapter
 * Created by Zheng on 2016/12/21.
 */
public class TableFilesAdapter extends MyBaseAdapter{

    private FilesInfoTableHelper mHelper;

    private ProgressBar progressBar;

    private MyApplication mApplication=MyApplication.getSingleton();

    public TableFilesAdapter(Context context, List data) {
        super(context, data);
        mHelper=new FilesInfoTableHelper(mContext);
    }

    @Override
    public View createView(LayoutInflater inflater, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_tablefiles,parent,false);
            holder=new ViewHolder();
            holder.mIconIv= (ImageView) convertView.findViewById(R.id.item_tablefiles_icon);
            holder.mFileNameTv= (TextView) convertView.findViewById(R.id.item_tablefiles_name);
            holder.mFileSizeTv= (TextView) convertView.findViewById(R.id.item_tablefiles_size);
            holder.mFileTimeTv= (TextView) convertView.findViewById(R.id.item_tablefiles_time);
            holder.mOpenBtn= (Button) convertView.findViewById(R.id.item_tablefiles_open);
            holder.mProgressBar= (ProgressBar) convertView.findViewById(R.id.item_tablefiles_progress);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        progressBar=holder.mProgressBar;

        /** 附件信息 */
        final FileEntity entity= (FileEntity) mData.get(position);

        /** 根据文件后缀判断文件类型，设置对应的文件图标 */
        if(entity.getFileName().endsWith(".rar")||entity.getFileName().endsWith(".zip")){
            holder.mIconIv.setImageResource(R.mipmap.compressed_file_icon);
        }

        /** 设置文件名 */
        holder.mFileNameTv.setText(entity.getFileName());

        /** 设置文件大小 */
        //格式化文件大小，保留两位小数
        Float mFileSize=Float.valueOf(entity.getFileSize())/1024;
        int scale = 2;//设置位数
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        BigDecimal bd = new BigDecimal((double)mFileSize);
        bd = bd.setScale(scale, roundingMode);
        mFileSize = bd.floatValue();

        holder.mFileSizeTv.setText(mFileSize+"M");
        if("0.0".equals(""+mFileSize)){//如果文件小到被忽略，将文件大小设置为0.1M
            holder.mFileSizeTv.setText(0.01+"M");
        }

        /** 设置文件上传时间 */
        holder.mFileTimeTv.setText(entity.getFileUploadTime());

        /** 设置下载按钮点击事件 */
        if(!mHelper.queryFileInfo(entity.getFileId())){//如果附件数据库中没有此附件，说明此附件还未下载，点击下载
//            final ViewHolder mHolder=holder;

            Log.e("MAP",mApplication.getMap().toString());

            if(mApplication.containInQueue(entity.getFileId())){

                progressBar.setProgress(FileDownloader.getImpl().replaceListener(mApplication.getDownloadId(entity.getFileId()),
                        getFileDownloadListener(entity)));

                holder.mOpenBtn.setText("暂停");
                holder.mOpenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FileDownloader.getImpl().pause(mApplication.getDownloadId(entity.getFileId()));
                        mApplication.removeFromQueue(entity.getFileId());
                        notifyDataSetChanged();
                    }
                });
            }else{
                holder.mOpenBtn.setText("下载");
                holder.mOpenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getDownTask(entity).start();
                    }
                });
            }

//            holder.mOpenBtn.setText("下载");
//            holder.mOpenBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    getDownTask(entity, progressBar).start();
//                }
//            });

        }else{//如果附件数据库中含有此附件，说明此附件已经下载，点击查看
            holder.mOpenBtn.setVisibility(View.VISIBLE);
            holder.mOpenBtn.setText("查看");
            holder.mOpenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file=new File(AppConstants.APP_FILE_DOWNLOAD_FOLDER+entity.getFileName());
                    if(file.exists()){
                        openFile(mContext, file);
                    }else{
                        Toast.makeText(mContext,"找不到文件，请尝试重新下载",Toast.LENGTH_SHORT).show();
                        mHelper.deleteFileInfo(entity.getFileId());
                        notifyDataSetChanged();
                    }
                }
            });
        }

        return convertView;
    }

    class ViewHolder{
        ImageView mIconIv;
        TextView mFileNameTv;
        TextView mFileSizeTv;
        TextView mFileTimeTv;
        Button mOpenBtn;
        ProgressBar mProgressBar;
    }

    /**
     * 获取下载任务
     * @param entity entity
     * @return BaseDownloadTask
     */
    private BaseDownloadTask getDownTask(final FileEntity entity){

        return FileDownloader.getImpl()
                .create(Tools.jointIpAddress() + NetConstants.FILES_DOWNLOAD_PORT + entity.getFileId())
                .setPath(AppConstants.APP_FILE_DOWNLOAD_FOLDER + entity.getFileName(), false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(500)
                .setListener(getFileDownloadListener(entity));
    }

    private FileDownloadListener getFileDownloadListener(final FileEntity entity){

        return new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void started(BaseDownloadTask task) {
                super.started(task);
                Toast.makeText(mContext,"开始下载",Toast.LENGTH_SHORT).show();

                mApplication.put2Queue(entity.getFileId(), task.getId());

                notifyDataSetChanged();

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                progressBar.setMax(totalBytes);
                progressBar.setProgress(soFarBytes);
//                        holder.mOpenBtn.setText(""+task.getSpeed()+"K/s");
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Toast.makeText(mContext,"下载完成",Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.INVISIBLE);

                mHelper.insertFileInfo(entity);

                mApplication.removeFromQueue(entity.getFilePath());

                notifyDataSetChanged();
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Toast.makeText(mContext,"已暂停",Toast.LENGTH_SHORT).show();

//                        mApplication.removeFromQueue(entity.getFilePath());
//
//                        notifyDataSetChanged();
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Toast.makeText(mContext,"下载失败",Toast.LENGTH_SHORT).show();

                mApplication.removeFromQueue(entity.getFilePath());

                notifyDataSetChanged();
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Toast.makeText(mContext,"此文件可能已经存在",Toast.LENGTH_SHORT).show();

                mApplication.removeFromQueue(entity.getFilePath());

                notifyDataSetChanged();
            }
        };
    }

    /**
     * 打开文件
     * @param file
     */
    private void openFile(Context context,File file){

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
        try{
            context.startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(context,"未找到支持此文件类型的软件",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     * @param file
     */
    private String getMIMEType(File file) {

        String type="*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if(dotIndex < 0){
            return type;
        }
    /* 获取文件的后缀名 */
        String end=fName.substring(dotIndex,fName.length()).toLowerCase();
        if(end=="")return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for(int i=0;i<MIME_MapTable.length;i++){ //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if(end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 各种类型的文件对应的MIME
     */
    private final String[][] MIME_MapTable={
            //{后缀名， MIME类型}
            {".3gp",    "video/3gpp"},
            {".apk",    "application/vnd.android.package-archive"},
            {".asf",    "video/x-ms-asf"},
            {".avi",    "video/x-msvideo"},
            {".bin",    "application/octet-stream"},
            {".bmp",    "image/bmp"},
            {".c",      "text/plain"},
            {".class",  "application/octet-stream"},
            {".conf",   "text/plain"},
            {".cpp",    "text/plain"},
            {".doc",    "application/msword"},
            {".docx",   "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls",    "application/vnd.ms-excel"},
            {".xlsx",   "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe",    "application/octet-stream"},
            {".gif",    "image/gif"},
            {".gtar",   "application/x-gtar"},
            {".gz",     "application/x-gzip"},
            {".h",      "text/plain"},
            {".htm",    "text/html"},
            {".html",   "text/html"},
            {".jar",    "application/java-archive"},
            {".java",   "text/plain"},
            {".jpeg",   "image/jpeg"},
            {".jpg",    "image/jpeg"},
            {".js",     "application/x-javascript"},
            {".log",    "text/plain"},
            {".m3u",    "audio/x-mpegurl"},
            {".m4a",    "audio/mp4a-latm"},
            {".m4b",    "audio/mp4a-latm"},
            {".m4p",    "audio/mp4a-latm"},
            {".m4u",    "video/vnd.mpegurl"},
            {".m4v",    "video/x-m4v"},
            {".mov",    "video/quicktime"},
            {".mp2",    "audio/x-mpeg"},
            {".mp3",    "audio/x-mpeg"},
            {".mp4",    "video/mp4"},
            {".mpc",    "application/vnd.mpohun.certificate"},
            {".mpe",    "video/mpeg"},
            {".mpeg",   "video/mpeg"},
            {".mpg",    "video/mpeg"},
            {".mpg4",   "video/mp4"},
            {".mpga",   "audio/mpeg"},
            {".msg",    "application/vnd.ms-outlook"},
            {".ogg",    "audio/ogg"},
            {".pdf",    "application/pdf"},
            {".png",    "image/png"},
            {".pps",    "application/vnd.ms-powerpoint"},
            {".ppt",    "application/vnd.ms-powerpoint"},
            {".pptx",   "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop",   "text/plain"},
            {".rar",    "application/x-rar-compressed"},
            {".rc",     "text/plain"},
            {".rmvb",   "audio/x-pn-realaudio"},
            {".rtf",    "application/rtf"},
            {".sh",     "text/plain"},
            {".tar",    "application/x-tar"},
            {".tgz",    "application/x-compressed"},
            {".txt",    "text/plain"},
            {".wav",    "audio/x-wav"},
            {".wma",    "audio/x-ms-wma"},
            {".wmv",    "audio/x-ms-wmv"},
            {".wps",    "application/vnd.ms-works"},
            {".xml",    "text/plain"},
            {".z",      "application/x-compress"},
            {".zip",    "application/x-zip-compressed"},
            {"",        "*/*"}
    };

}
