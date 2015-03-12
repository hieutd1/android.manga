package com.vnfapps.hide.manga.views.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.ortiz.touch.TouchImageView;
import com.vnfapps.hide.manga.interfaces.ResponseKey;
import com.vnfapps.hide.manga.utils.BitmapLruCache;
import com.vnfapps.hide.manga.utils.Logger;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by hide on 11/03/2015.
 */
public class ChapterPaperAdapter extends PagerAdapter implements ResponseKey{
    private final static String TAG = ChapterPaperAdapter.class.getName();

    ImageLoader imageLoader = null;
    List<String> images = null;

    /*
     ***************************************************************
     * constructor
     ***************************************************************
     */
    public ChapterPaperAdapter(Context context) {
        if(imageLoader == null){
            ImageLoader.ImageCache imageCache = new BitmapLruCache();
            imageLoader = new ImageLoader(Volley.newRequestQueue(context), imageCache);
        }
        images = new ArrayList<>();
    }

    public void setData(List<String> images){
        Logger.d(TAG+".setData", "start");
        this.images = images;
        Logger.d(TAG+".setData", images.toString());
        notifyDataSetChanged();
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        Logger.d(TAG+".getCount", "start");
        if(this.images != null) {
            Logger.d(TAG+".getCount", images.size()+"");
            return this.images.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final TouchImageView image = new TouchImageView(container.getContext());
        imageLoader.get(images.get(position), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean arg1) {
                try {
                    image.setImageBitmap(imageContainer.getBitmap());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof TouchImageView) {
            TouchImageView image = (TouchImageView) object;
            image.setImageBitmap(null);
        }
        container.removeView((View) object);
    }
}
