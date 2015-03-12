package com.vnfapps.hide.manga.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hide on 06/03/2015.
 */
public class Story extends Post {

    private String author;
    private String featuredImg;
    private int type;
    private int status;

    public Story(JSONObject jsonObject) throws Exception {
        super(jsonObject);
    }

    public Story(String jsonString) throws Exception {
        super(jsonString);
    }

    @Override
    public boolean setData(JSONObject jsonObject) {
        if(super.setData(jsonObject)){
            try {
                author = jsonObject.getString("author");
                featuredImg = jsonObject.getString("featured_img");
                type = jsonObject.getInt("type");
                status = jsonObject.getInt("status_id");
                return true;

            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public String getAuthor() {
        return author;
    }

    public String getFeaturedImg() {
        return featuredImg;
    }

    public int getType() {
        return type;
    }

    public int getStatus() {
        return status;
    }
}
