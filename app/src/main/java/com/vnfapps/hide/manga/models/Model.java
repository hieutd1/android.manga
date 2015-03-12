package com.vnfapps.hide.manga.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by hide on 06/03/2015.
 */
public interface Model extends Serializable{
    public static final String TAG = Model.class.getName();
    public boolean setData(String jsonString) throws JSONException;
    public boolean setData(JSONObject jsonObject) throws JSONException;
}
