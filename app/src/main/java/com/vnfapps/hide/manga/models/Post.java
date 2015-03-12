package com.vnfapps.hide.manga.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hide on 06/03/2015.
 */
public class Post implements Model {
    private double id;
    private String name;
    private String content;
    private String slug;

    public Post() {}
    public Post(String jsonString) throws Exception{
        setData(jsonString);
    }

    public Post(JSONObject jsonObject) throws Exception {
        setData(jsonObject);
    }

    @Override
    public boolean setData(String jsonString) {
        try{
            JSONObject object = new JSONObject(jsonString);
            return setData(object);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean setData(JSONObject jsonObject) {
        try {
            JSONObject post = jsonObject.getJSONObject("post");
            id          = post.getDouble("post_id");
            name        = post.getString("name");
            content     = post.getString("content");
            slug        = post.getString("slug");
            return true;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }

    public double getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getSlug() {
        return slug;
    }
}
