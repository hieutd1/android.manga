package com.vnfapps.hide.manga.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hide on 06/03/2015.
 */
public class Chapter extends Post{
    private double storyId;
    private double no;

    public Chapter(String jsonString) throws Exception {
        super(jsonString);
    }

    public Chapter(JSONObject jsonObject) throws Exception {
        super(jsonObject);
    }

    @Override
    public boolean setData(JSONObject jsonObject) {
        if(super.setData(jsonObject)){
            try {
                storyId = jsonObject.getDouble("story_id");
                no = jsonObject.getDouble("no");
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public double getStoryId() {
        return storyId;
    }

    public double getNo() {
        return no;
    }
}
