package com.vnfapps.hide.manga.controllers;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vnfapps.hide.manga.enums.ActionType;
import com.vnfapps.hide.manga.enums.URL;
import com.vnfapps.hide.manga.interfaces.RequestListener;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.Logger;


import java.util.ArrayList;

/**
 * Created by hide on 07/03/2015.
 */
public class RequestController implements Response.ErrorListener{

    private static final String TAG = RequestController.class.getName();

    //singleton
    private static RequestController   instance        = null;

    //local variable
    private Context             context             = null;
    private RequestListener     requestListener     = null;
    private RequestQueue        requestQueue        = null;
    private URL                 url                 = null;
    private ArrayList<String>   tags                = null;
    private ActionType          actionType          = null;

    private ResponseDTO         responseDTO         = null;

    public ResponseDTO getResponseDTO() {
        return responseDTO;
    }

    private RequestController(Context context, RequestListener requestListener) {
        this.context = context;
        this.requestListener = requestListener;
        this.requestQueue = Volley.newRequestQueue(this.context);
        tags = new ArrayList<String>();
        responseDTO = new ResponseDTO();
        url = URL.NOTHING;
        actionType = ActionType.DO_NOTHING;
    }
    /*
     * RequestController singleton init
     */
    public synchronized static void init(Context context, RequestListener requestListener){
           instance = new RequestController(context, requestListener);
    }

    /*
     * Singleton getInstance
     */
    public synchronized static RequestController getInstance(){
        return instance;
    }

    public void request(ActionType actionType, URL url, Object... params) throws Exception{
        requestListener.onRequest();
        clearData();
        this.actionType = actionType;
        this.url = url;
        String fullUrl = String.format(this.url.getCurURL(), params);
        Logger.i(TAG, "request: url[GET]: "+ fullUrl);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,fullUrl, new ResponseListener(), this);
        stringRequest.setTag(this.url.curURL);
        requestQueue.add(stringRequest);
        tags.add(this.url.curURL);
    }
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        volleyError.printStackTrace();
    }

    protected class ResponseListener implements Response.Listener<String>{

        @Override
        public void onResponse(String jsonString){
            responseDTO.setData(jsonString);
            tags.remove(url.curURL);
            Logger.i(TAG, "responseDTO: "+responseDTO.message + "\n" + responseDTO.toString());
            try {
                requestListener.onResponse(actionType, responseDTO);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void clearData(){
        responseDTO.clear();
        url = URL.NOTHING;
        actionType = ActionType.DO_NOTHING;
        for (String tag: tags) {
            requestQueue.cancelAll(tag);
        }
        tags.clear();
    }
}
