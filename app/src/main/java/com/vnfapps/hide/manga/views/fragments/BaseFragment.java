package com.vnfapps.hide.manga.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.vnfapps.hide.manga.interfaces.ResponseKey;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.BitmapLruCache;


public abstract class BaseFragment extends Fragment implements ResponseKey {
    public static final String TAG = BaseFragment.class.getName();
    /*
     * **********************************************************************
     * Variables
     * **********************************************************************
     */

    protected Handler           handler           = null;
    protected ImageLoader       imageLoader       = null;
    protected Resources         resources         = null;
    protected LayoutInflater    inflater          = null;
    protected ResponseDTO       responseDTO       = null;
    
    /*
     * **********************************************************************
     * Life Cycle
     * **********************************************************************
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            if (handler == null) {
                handler = new Handler();
            }
            if (imageLoader == null) {
                ImageLoader.ImageCache imageCache = new BitmapLruCache();
                imageLoader = new ImageLoader(Volley.newRequestQueue(activity), imageCache);
            }
            resources = activity.getResources();
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            responseDTO = new ResponseDTO();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        /*try {
            Utils.clearHandler(handler);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    /*
     * **********************************************************************
     * Functions
     * **********************************************************************
     */
    public void refresh(){
        setData(responseDTO);
    };

    public void setData(ResponseDTO responseDTO){
        this.responseDTO = new ResponseDTO();
        this.responseDTO.setData(responseDTO.toString());
    }

    public ResponseDTO getData(){
        return responseDTO;
    }

    /*
         * **********************************************************************
         * Event
         * **********************************************************************
         */
    public boolean onBackPressed() {
        return false;
    }
    
    /*
     * **********************************************************************
     * Implement Function
     * **********************************************************************
     */
    /*public void onResponse(ResponseDTO responseDTO) throws Exception {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void onError(String message) throws Exception {
        try {
            *//*if (iManga != null) {
                iManga.showError(message);
            }*//*
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
