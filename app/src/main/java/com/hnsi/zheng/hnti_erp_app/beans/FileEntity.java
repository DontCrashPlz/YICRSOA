package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 审批附件数据实体
 * Created by Zheng on 2016/12/22.
 */
public class FileEntity {
    String fileNum="";
    String fileName="";
    String fileUploadTime="";
    String filePath="";
    String fileSize="";
    String fileId="";

    public String getFileNum() {
        return fileNum;
    }

    public void setFileNum(String fileNum) {
        this.fileNum = fileNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(String fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "fileNum='" + fileNum + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileUploadTime='" + fileUploadTime + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize='" + fileSize + '\'' +
                ", fileId='" + fileId + '\'' +
                '}';
    }

}


//    "tableTitle":[
//        "序号",
//        "附件名称",
//        "上传时间",
//        "附件路径",(已废弃)
//        "附件大小"
//        "downloadId"(用于组建download url)
//        ]

//    [
//      "1",
//      "疯狂Java第3版 .pdf",
//      "2017-02-06",
//      "\/uploadfile\/XMYY_YYSQ\/20170206\/143_B502626088_370857395_undefined.pdf",
//      "362165",
//      "49523"
//     ]