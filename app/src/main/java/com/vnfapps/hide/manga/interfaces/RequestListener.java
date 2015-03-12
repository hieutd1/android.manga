package com.vnfapps.hide.manga.interfaces;

import com.vnfapps.hide.manga.enums.ActionType;
import com.vnfapps.hide.manga.models.ResponseDTO;

/**
 * Created by hide on 09/03/2015.
 */
public interface RequestListener {
    public void onRequest() throws Exception;
    public void onResponse(ActionType actionType, final ResponseDTO responseDTO) throws Exception;
    public void onError() throws Exception;
}
