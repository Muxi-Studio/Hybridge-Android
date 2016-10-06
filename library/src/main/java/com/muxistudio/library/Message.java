package com.muxistudio.library;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ybao on 16/8/27.
 * jsbridge 通信的数据结构
 */
public class Message {

    private String callbackId;
    private String responseId;
    private String responseData;
    private String data;
    private String handlerName;

    private static final String CALLBACK_ID_STR = "callbackId";
    private static final String RESPONSE_ID_STR = "responseId";
    private static final String RESPONSE_DATA_STR = "responseData";
    private static final String DATA_STR = "data";
    private static final String HANDLER_NAME_STR = "handlerName";

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    //序列化
    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(CALLBACK_ID_STR, this.getCallbackId());
            jsonObject.put(RESPONSE_ID_STR, this.getResponseId());
            jsonObject.put(RESPONSE_DATA_STR, this.getResponseData());
            jsonObject.put(DATA_STR, this.getData());
            jsonObject.put(HANDLER_NAME_STR, this.getHandlerName());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    //反序列化
    public static Message toMessage(String json) {
        Message message = new Message();
        try {
            JSONObject jsonObject = new JSONObject(json);
            message.setCallbackId(jsonObject.has(CALLBACK_ID_STR) ? jsonObject.getString(CALLBACK_ID_STR) : null);
            message.setResponseId(jsonObject.has(RESPONSE_ID_STR) ? jsonObject.getString(RESPONSE_ID_STR) : null);
            message.setResponseData(jsonObject.has(RESPONSE_DATA_STR) ? jsonObject.getString(RESPONSE_DATA_STR) : null);
            message.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR) : null);
            message.setHandlerName(jsonObject.has(HANDLER_NAME_STR) ? jsonObject.getString(HANDLER_NAME_STR) : null);
            return message;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Message> toList(String json) {
        List<Message> messages = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Message message = new Message();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                message.setCallbackId(jsonObject.has(CALLBACK_ID_STR) ? jsonObject.getString(CALLBACK_ID_STR) : null);
                message.setResponseId(jsonObject.has(RESPONSE_ID_STR) ? jsonObject.getString(RESPONSE_ID_STR) : null);
                message.setResponseData(jsonObject.has(RESPONSE_DATA_STR) ? jsonObject.getString(RESPONSE_DATA_STR) : null);
                message.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR) : null);
                message.setHandlerName(jsonObject.has(HANDLER_NAME_STR) ? jsonObject.getString(HANDLER_NAME_STR) : null);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messages;
    }
    //    private String callbackId; //callbackId
//    private String responseId; //responseId
//    private String responseData; //responseData
//    private String data; //data of message
//    private String handlerName; //name of handler
//
//    private final static String CALLBACK_ID_STR = "callbackId";
//    private final static String RESPONSE_ID_STR = "responseId";
//    private final static String RESPONSE_DATA_STR = "responseData";
//    private final static String DATA_STR = "data";
//    private final static String HANDLER_NAME_STR = "handlerName";
//
//    public String getResponseId() {
//        return responseId;
//    }
//    public void setResponseId(String responseId) {
//        this.responseId = responseId;
//    }
//    public String getResponseData() {
//        return responseData;
//    }
//    public void setResponseData(String responseData) {
//        this.responseData = responseData;
//    }
//    public String getCallbackId() {
//        return callbackId;
//    }
//    public void setCallbackId(String callbackId) {
//        this.callbackId = callbackId;
//    }
//    public String getData() {
//        return data;
//    }
//    public void setData(String data) {
//        this.data = data;
//    }
//    public String getHandlerName() {
//        return handlerName;
//    }
//    public void setHandlerName(String handlerName) {
//        this.handlerName = handlerName;
//    }
//
//    public String toJson() {
//        JSONObject jsonObject= new JSONObject();
//        try {
//            jsonObject.put(CALLBACK_ID_STR, getCallbackId());
//            jsonObject.put(DATA_STR, getData());
//            jsonObject.put(HANDLER_NAME_STR, getHandlerName());
//            jsonObject.put(RESPONSE_DATA_STR, getResponseData());
//            jsonObject.put(RESPONSE_ID_STR, getResponseId());
//            return jsonObject.toString();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static Message toObject(String jsonStr) {
//        Message m =  new Message();
//        try {
//            JSONObject jsonObject = new JSONObject(jsonStr);
//            m.setHandlerName(jsonObject.has(HANDLER_NAME_STR) ? jsonObject.getString(HANDLER_NAME_STR):null);
//            m.setCallbackId(jsonObject.has(CALLBACK_ID_STR) ? jsonObject.getString(CALLBACK_ID_STR):null);
//            m.setResponseData(jsonObject.has(RESPONSE_DATA_STR) ? jsonObject.getString(RESPONSE_DATA_STR):null);
//            m.setResponseId(jsonObject.has(RESPONSE_ID_STR) ? jsonObject.getString(RESPONSE_ID_STR):null);
//            m.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR):null);
//            return m;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return m;
//    }
//
//    public static List<Message> toArrayList(String jsonStr){
//        List<Message> list = new ArrayList<Message>();
//        try {
//            JSONArray jsonArray = new JSONArray(jsonStr);
//            for(int i = 0; i < jsonArray.length(); i++){
//                Message m = new Message();
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                m.setHandlerName(jsonObject.has(HANDLER_NAME_STR) ? jsonObject.getString(HANDLER_NAME_STR):null);
//                m.setCallbackId(jsonObject.has(CALLBACK_ID_STR) ? jsonObject.getString(CALLBACK_ID_STR):null);
//                m.setResponseData(jsonObject.has(RESPONSE_DATA_STR) ? jsonObject.getString(RESPONSE_DATA_STR):null);
//                m.setResponseId(jsonObject.has(RESPONSE_ID_STR) ? jsonObject.getString(RESPONSE_ID_STR):null);
//                m.setData(jsonObject.has(DATA_STR) ? jsonObject.getString(DATA_STR):null);
//                list.add(m);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
}
