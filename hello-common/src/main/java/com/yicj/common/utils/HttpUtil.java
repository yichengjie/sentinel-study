package com.yicj.common.utils;

import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yicj
 * @date 2023年09月18日 10:18
 */
public class HttpUtil {

    public static final MediaType JSON = MediaType.parse("application/json");

    private static final OkHttpClient CLIENT = new OkHttpClient().newBuilder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool())
            .build();

    /**
     * get 发送请求
     * @param url 请求地址
     * @param headers  header参数
     * @param params 请求参数 （可为null）
     * @return 返回byte数据
     * @throws IOException
     */
    public static byte[] get(
            String url,
            Map<String,String> headers,
            Map<String,String> params) throws IOException {
        // 创建get请求实例
        String paramSection = assembleUrlParams(params);
        return doGet(url + paramSection, headers) ;
    }


    /**
     * post 发送json body请求
     * @param url 请求地址
     * @param headers header参数
     * @param json body中json类型的数据
     * @return 返回byte数据
     * @throws IOException
     */
    public static byte[] postJson(String url, Map<String,String> headers, String json) throws IOException {
        // 创建get请求实例
        RequestBody requestBody = null ;
        if (StringUtils.isNotBlank(json)){
            requestBody = RequestBody.create(JSON, json);
        }
        return doPost(url, headers, requestBody) ;
    }

    /**
     * post 上传文件
     * @param url 上传地址
     * @param headers header参数
     * @param content 文件字节数组
     * @param fileName 文件名称
     * @return 返回字节数组
     * @throws IOException
     */
    public static byte[] postFile(
            String url,
            Map<String,String> headers,
            byte[] content,
            String fileName) throws IOException {
        // 创建get请求实例
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("file", fileName,
                RequestBody.create(MediaType.parse("multipart/form-data"), content));
        RequestBody requestBody = builder.build();
        return doPost(url, headers, requestBody) ;
    }

    /**
     * put 发送json body请求
     * @param url 请求地址
     * @param headers header参数
     * @param json body中json类型的数据
     * @return 返回字节数组
     * @throws IOException
     */
    public static byte[] putJson(
            String url,
            Map<String,String> headers,
            String json) throws IOException {
        // 创建get请求实例
        RequestBody requestBody = null ;
        if (json != null){
            requestBody = RequestBody.create(JSON, json);
        }
        return doPut(url, headers, requestBody) ;
    }

    /**
     * put 上传文件
     * @param url 上传地址
     * @param headers header参数
     * @param content 文件字节数组
     * @param fileName 文件名称
     * @return 返回字节数组
     * @throws IOException
     */
    public static byte[] putFile(
            String url,
            Map<String,String> headers,
            byte[] content,
            String fileName) throws IOException {
        // 创建get请求实例
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("file", fileName,
                    RequestBody.create(MediaType.parse("multipart/form-data"), content));
        RequestBody requestBody = builder.build();
        return doPut(url, headers, requestBody) ;
    }

    private static byte[] doPost(
            String url,
            Map<String,String> headers,
            RequestBody requestBody) throws IOException {
        return doExecute(url, headers, requestBody, "POST") ;
    }

    private static byte[] doPut(
            String url,
            Map<String,String> headers,
            RequestBody requestBody) throws IOException {
       return doExecute(url, headers, requestBody, "PUT") ;
    }

    private static byte[] doGet(
            String url,
            Map<String,String> headers) throws IOException{
        return doExecute(url, headers, null, "GET") ;
    }

    public static byte[] doExecute(
            String url,
            Map<String,String> headers,
            RequestBody requestBody,
            String method) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .method(method, requestBody);
        fillHeaders(requestBuilder, headers);
        try (Response response = CLIENT.newCall(requestBuilder.build()).execute()) {
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code " + response);
            }
            return response.body().bytes();
        }
    }

    private static void fillHeaders(Request.Builder builder, Map<String,String> headers){
        if (headers != null){
            headers.forEach(builder::header);
        }
    }

    private static String assembleUrlParams(Map<String, String> bodyParams) {
        if (bodyParams == null || bodyParams.size() == 0){
            return "";
        }
        //1.添加请求参数
        //遍历map中所有参数到builder
        StringBuilder stringBuffer = new StringBuilder("?");
        for (String key : bodyParams.keySet()) {
            if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(bodyParams.get(key))) {
                //如果参数不是null并且不是""，就拼接起来
                stringBuffer.append("&");
                stringBuffer.append(key);
                stringBuffer.append("=");
                stringBuffer.append(bodyParams.get(key));
            }
        }
        return stringBuffer.toString();
    }
}
