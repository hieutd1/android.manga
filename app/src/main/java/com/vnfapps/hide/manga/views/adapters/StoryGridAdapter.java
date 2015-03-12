package com.vnfapps.hide.manga.views.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.vnfapps.hide.manga.R;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hide on 09/03/2015.
 */
public class StoryGridAdapter extends BaseAdapter{
    private static final String TAG = StoryGridAdapter.class.getName();

    public StoryGridAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        if(responseDTO.response!=null){
            JSONArray result = (JSONArray)responseDTO.response;
            return result.length();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(responseDTO.response!=null){
            try {
                //Logger.d(TAG+".getItem", ((JSONArray) responseDTO.response).getJSONObject(position).toString());
                return ((JSONArray) responseDTO.response).getJSONObject(position);
            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(responseDTO.response!=null){
            try {
                Logger.i(TAG+".getItemId", responseDTO.toString());
                JSONObject object = ((JSONArray)responseDTO.response).getJSONObject(position);
                return object.getLong(StoryKeys.ID);
            }catch (JSONException e){
                e.printStackTrace();
                return -1;
            }
        }
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            ViewHolder viewHolder = null;
            if(convertView == null){
                convertView = inflater.inflate(R.layout.story_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.image = (NetworkImageView) convertView.findViewById(R.id.featured_img);
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String featuredImg = "";
            String name = "";
            JSONObject result = (JSONObject)((JSONArray) responseDTO.response).getJSONObject(position);

            name = result.getJSONObject(PostKeys.POST).getString(PostKeys.NAME);
            featuredImg = result.getString(StoryKeys.FEATURED_IMG);

            viewHolder.name.setText(name);
            if(imageLoader!=null){
                viewHolder.image.setImageUrl(featuredImg, imageLoader);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return convertView;
    }


    /*
         * **********************************************************************
         * Inner class
         * **********************************************************************
         */
    private static class ViewHolder {
        /*
         * **********************************************************************
         * Variables
         * **********************************************************************
         */
        public NetworkImageView image     = null;
        public TextView name      = null;
    }
}
