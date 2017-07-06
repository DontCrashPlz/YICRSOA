package com.hnsi.zheng.hnti_erp_app.beans;

/**
 * 审批附件数据实体
 * Created by Zheng on 2016/12/22.
 * （废弃，改为使用FileEntity）
 */
@Deprecated
class FilesEntity {
    String fileNum="";
    String fileName="";
    String fileUploadTime="";
    String filePath="";
    String fileSize="";

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

    @Override
    public String toString() {
        return "FilesEntity{" +
                "fileNum='" + fileNum + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileUploadTime='" + fileUploadTime + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSize='" + fileSize + '\'' +
                '}';
    }
}


//    "tableTitle":[
//        "序号",
//        "附件名称",
//        "上传时间",
//        "附件路径",
//        "附件大小"
//        ]

//    [
//        "1",
//        "综合查询组件使用说明.docx",
//        "2016-12-02",
//        "/uploadfile/oa_mic/20161202/1_B1352991952_15179.docx",
//        "14"
//        ]