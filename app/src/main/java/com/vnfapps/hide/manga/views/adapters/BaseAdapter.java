package com.vnfapps.hide.manga.views.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.vnfapps.hide.manga.interfaces.ResponseKey;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.BitmapLruCache;
import com.vnfapps.hide.manga.utils.Logger;


public class BaseAdapter extends android.widget.BaseAdapter implements ResponseKey {
    private static final String TAG = BaseAdapter.class.getName();
    /*
     * **********************************************************************
     * Variables
     * **********************************************************************
     */
    protected ResponseDTO           responseDTO = null;
    protected Context               context     = null;
    protected LayoutInflater        inflater    = null;
    protected Resources             resources   = null;

    protected ImageLoader           imageLoader = null;
    
    protected String                packageName = null;

    public Object getData() {
        return responseDTO.response;
    }

    /*
         * **********************************************************************
         * Constructor
         * **********************************************************************
         */
    public BaseAdapter() {
        super();
        responseDTO = new ResponseDTO();
    }
    
    public BaseAdapter(Context context) {
        try {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
            this.resources = context.getResources();
            responseDTO = new ResponseDTO();
            this.packageName = context.getPackageName();
            
            if (imageLoader == null) {
                ImageLoader.ImageCache imageCache = new BitmapLruCache();
                imageLoader = new ImageLoader(Volley.newRequestQueue(context), imageCache);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(ResponseDTO responseDTO) {
        Logger.i(TAG+".setData", "start");
        this.responseDTO = responseDTO;
        this.notifyDataSetChanged();
    }

    /*
     * **********************************************************************
     * Override
     * **********************************************************************
     */
    @Override
    public int getCount() {
        return 0;
    }
    
    @Override
    public Object getItem(int position) {
        return null;
    }
    
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


}
