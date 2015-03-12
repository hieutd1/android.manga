package com.vnfapps.hide.manga.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vnfapps.hide.manga.R;
import com.vnfapps.hide.manga.interfaces.ResponseKey;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by hide on 10/03/2015.
 */
public class ChapterListAdapter extends android.widget.BaseAdapter implements ResponseKey {
    private static final String TAG = ChapterListAdapter.class.getName();
    private Context context;
    private ResponseDTO responseDTO;

    public ChapterListAdapter(Context context) {
        this.context = context;
    }

    public void setData(ResponseDTO responseDTO){
        this.responseDTO = responseDTO;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        try {
            if (responseDTO != null) {
                JSONObject story = (JSONObject) responseDTO.response;
                JSONArray chapters = story.getJSONArray(StoryKeys.CHAPTERS);
                return chapters.length();
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        try {
            if (responseDTO != null) {
                JSONObject story = (JSONObject) responseDTO.response;
                JSONArray chapters = story.getJSONArray(StoryKeys.CHAPTERS);
                JSONObject post = story.getJSONObject(PostKeys.POST);
                JSONObject item = new JSONObject();
                item.put(ChapterKeys.NO,chapters.getJSONObject(position).getInt(ChapterKeys.NO));
                item.put(PostKeys.SLUG,post.getString(PostKeys.SLUG));
                return item;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        try {
            if (responseDTO != null) {
                JSONObject story = (JSONObject) responseDTO.response;
                JSONArray chapters = story.getJSONArray(StoryKeys.CHAPTERS);
                return chapters.getJSONObject(position).getLong(ChapterKeys.ID);
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{

            ViewHolder viewHolder;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.chapter_item,parent,false);

                viewHolder = new ViewHolder();
                viewHolder.label = (TextView)convertView.findViewById(R.id.chapter_label);
                viewHolder.createdAt = (TextView)convertView.findViewById(R.id.created_at);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String label = "Chương ";
            String createdAt = "";

            JSONObject story = (JSONObject) responseDTO.response;
            JSONObject chapter = story.getJSONArray(StoryKeys.CHAPTERS).getJSONObject(position);
            JSONObject post = chapter.getJSONObject(PostKeys.POST);

            label+=chapter.getString("no");
            //Logger.i(TAG+".getView", post.get(PostKeys.NAME).toString());
            if(post.get(PostKeys.NAME)!=JSONObject.NULL){
                label+=(": "+post.getString(PostKeys.NAME));
            }
            createdAt=post.getString(PostKeys.CREATED_AT);
            viewHolder.label.setText(label);
            viewHolder.createdAt.setText(createdAt);
            convertView.setTag(viewHolder);

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    private static class ViewHolder {
        /*
         * **********************************************************************
         * Variables
         * **********************************************************************
         */
        public TextView label      = null;
        public TextView createdAt  = null;
    }
}
