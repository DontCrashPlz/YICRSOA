package com.hnsi.zheng.hnti_erp_app.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.hnsi.zheng.hnti_erp_app.app.NetConstants;
import com.hnsi.zheng.hnti_erp_app.beans.FormImage;
import com.hnsi.zheng.hnti_erp_app.utils.Tools;

public class UpLoadingHeadimgRequest extends MyJsonObjectRequest {

    private String BOUNDARY = "--------------data-divide-"; //数据分隔线
    private String MULTIPART_FORM_DATA = "multipart/form-data";

    private String filePath;

    public UpLoadingHeadimgRequest(String filePath, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(Method.POST, Tools.jointIpAddress()+ NetConstants.UPLOADING_HEADIMG_PORT, listener, errorListener);
        this.filePath = filePath ;
        setShouldCache(false);
        //设置请求的响应事件，因为文件上传需要较长的时间，所以在这里加大了，设为5秒
        setRetryPolicy(new DefaultRetryPolicy(5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

//    /**
//     * 这里开始解析数据
//     * @param response Response from the network
//     * @return
//     */
//    @Override
//    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//        try {
//            String mString =
//                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
//            JSONObject obj = null;
//			try {
//				obj = new JSONObject(mString);
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            Log.v("zgy", "====mString===" + mString);
//
//            return Response.success(obj,
//                    HttpHeaderParser.parseCacheHeaders(response));
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
//        }
//    }

//    /**
//     * 回调正确的数据
//     * @param response The parsed response returned by
//     */
//    @Override
//    protected void deliverResponse(JSONObject response) {
//        mListener.onResponse(response);
//    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        FormImage formImage =new FormImage(filePath);
        StringBuffer sb= new StringBuffer() ;
            /*第一行*/
        //`"--" + BOUNDARY + "\r\n"`
        sb.append("--"+BOUNDARY);
        sb.append("\r\n") ;
            /*第二行*/
        //Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"
        sb.append("Content-Disposition: form-data;");
        sb.append(" name=\"");
        sb.append(formImage.getName()) ;
        sb.append("\"") ;
        sb.append("; filename=\"") ;
        sb.append(formImage.getFileName()) ;
        sb.append("\"");
        sb.append("\r\n") ;
            /*第三行*/
        //Content-Type: 文件的 mime 类型 + "\r\n"
        sb.append("Content-Type: ");
        sb.append(formImage.getMime()) ;
        sb.append("\r\n") ;
            /*第四行*/
        //"\r\n"
        sb.append("\r\n") ;
        try {
            bos.write(sb.toString().getBytes("utf-8"));
                /*第五行*/
            //文件的二进制数据 + "\r\n"
            bos.write(formImage.getValue());
            bos.write("\r\n".getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*结尾行*/
        //`"--" + BOUNDARY + "--" + "\r\n"`
        String endLine = "--" + BOUNDARY + "--" + "\r\n" ;
        try {
            bos.write(endLine.toString().getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("zgy","=====formImage====\n"+bos.toString()) ;
        return bos.toByteArray();
    }
    //Content-Type: multipart/form-data; boundary=----------8888888888888
    @Override
    public String getBodyContentType() {
        return MULTIPART_FORM_DATA+"; boundary="+BOUNDARY;
    }
}