package com.muxistudio.library;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ybao on 16/10/11.
 */

public class Message {

    private static final String CALLBACK_ID = "callbackId";
    private static final String RESPONSE_ID = "responseId";
    private static final String DATA = "data";
    private static final String RESPONSE_DATA = "responseData";
    private static final String METHOD = "method";

    private int callbackId;
    private int responseId;
    private String data;
    private String responseData;
    private String method;

    public int getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(int callbackId) {
        this.callbackId = callbackId;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    //把 json 转化为 Message对象
    public static Message parseJSON(String json){
        try {
            JSONObject object = new JSONObject(json);
            Message msg = new Message();
            msg.setCallbackId(object.has(CALLBACK_ID) ? object.getInt(CALLBACK_ID) : -1);
            msg.setResponseId(object.has(RESPONSE_ID) ? object.getInt(RESPONSE_ID) : -1);
            msg.setData(object.has(DATA) ? object.getString(DATA) : null);
            msg.setResponseData(object.has(RESPONSE_DATA) ? object.getString(RESPONSE_DATA) : null);
            msg.setMethod(object.has(METHOD) ? object.getString(METHOD) : null);
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String stringifyJSON(Message msg){
        try {
            JSONObject object = new JSONObject();
            object.put(CALLBACK_ID,msg.getCallbackId());
            object.put(RESPONSE_ID,msg.getResponseId());
            object.put(DATA,msg.getData());
            object.put(RESPONSE_DATA,msg.getResponseData());
            object.put(METHOD,msg.getMethod());
            return object.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
