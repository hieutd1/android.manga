package com.vnfapps.hide.manga.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by hide on 06/03/2015.
 */
public class ResponseDTO implements Model {
    public static final String TAG = ResponseDTO.class.getName();

    public String      rawData;
    public String      tag;
    public int         code;
    public String      message;
    public Object      response;

    public ResponseDTO() {
        rawData     = "";
        tag         = "";
        code        = 0;
        message     = "";
        response    = null;
    }

    public ResponseDTO(String jsonString) {
        setData(jsonString);
    }

    public ResponseDTO(JSONObject jsonObject) {
        setData(jsonObject);
    }

    @Override
    public boolean setData(String jsonString){
        try{
            JSONObject object = new JSONObject(jsonString);
            return setData(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setData(JSONObject jsonObject){
        try{
            clear();
            rawData = jsonObject.toString();
            tag = jsonObject.getString("tag");
            code = jsonObject.getInt("code");
            message = jsonObject.getString("message");
            response = jsonObject.get("response");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return rawData;
    }

    public ResponseDTO clear(){
        rawData     = "";
        tag         = "";
        code        = 0;
        message     = "";
        response    = null;
        return this;
    }
}
