package com.lemon.easyApiTest.Utils;

import com.alibaba.fastjson.JSONObject;
import com.lemon.easyApiTest.pojo.CaseInfo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpUtils {
    public static String responseBody;
    public static String call(CaseInfo caseInfo,Map<String,String> headers){
        try {
            //1.创建默认请求头对象，并且添加X-Lemonban-media-Type
            String params = caseInfo.getParams();
            String url = caseInfo.getUrl();
            String type = caseInfo.getType();
            //2.判断请求方式，如果是post
            if("post".equals(caseInfo.getType())){
                String contentType = caseInfo.getContentType();
                //2.1判断参数类型，如果是json
                if("json".equals(contentType)){
                    //2.2判断参数类型，如果是form
                    headers.put("Content-Type","application/json");
                }else if("form".equals(contentType)){ //判断是form类型
                    //json参数转换成key=value参数
                    params = jsonStr2KeyValuesStr(params);
                    //覆盖默认请求头
                    headers.put("Content-Type","application/x-www-form-urlencoded");
                }
                responseBody = post(url,params,headers);
            }else if("get".equals(caseInfo.getType())){
                responseBody = get(url,headers);
            }else if("patch".equals(caseInfo.getType())){
                responseBody = patch(url,params,headers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBody;
    }

    public static String get(String url,Map<String,String> headers) throws Exception{
        HttpGet get = new HttpGet(url);
        setHeaders(headers,get);
        CloseableHttpClient client = HttpClients.createDefault();//可关闭的客户端
        HttpResponse response = client.execute(get);
        return printResponse(response);
    }

    public static String post(String url, String param,Map<String,String> headers) throws Exception{
        HttpPost post = new HttpPost(url);
        setHeaders(headers,post);
        StringEntity body = new StringEntity(param,"utf-8");
        post.setEntity(body);
        CloseableHttpClient client = HttpClients.createDefault();//可关闭的客户端
        HttpResponse response = client.execute(post);
        return printResponse(response);
    }

    public static String patch(String url,String param,Map<String,String> headers) throws Exception {
        //1.创建请求对象，设置请求方法、接口URL地址
        HttpPatch patch = new HttpPatch(url);
        //2.设置请求体
        setHeaders(headers,patch);
        //3.设置请求体接口参数
        StringEntity body = new StringEntity(param,"utf-8");
        patch.setEntity(body);
        //4.点击发送
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(patch);
        return printResponse(response);
    }

    public static String printResponse(HttpResponse response) throws Exception {
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        Header[] heads = response.getAllHeaders();
        System.out.println(Arrays.toString(heads));
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity);
    }

    public static void setHeaders(Map<String,String> headers, HttpRequest request){
        Set<String> keySet = headers.keySet();
        for (String key:keySet){
            String value = headers.get(key);
            request.setHeader(key,value);
        }
    }

    //json参数转换成key=value参数
    public static String jsonStr2KeyValuesStr(String params){
        Map<String,String> map = JSONObject.parseObject(params, Map.class);
        Set<String> keySet = map.keySet();
        String formParams = "";
        for(String key : keySet){
            formParams += key + "=" + map.get(key) + "&";
        }
        params = formParams.substring(0,formParams.length()-1);
        return params;
    }
}

