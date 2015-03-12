package com.vnfapps.hide.manga.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vnfapps.hide.manga.R;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.Logger;
import com.vnfapps.hide.manga.views.adapters.ChapterPaperAdapter;
import com.vnfapps.hide.manga.views.customviews.ChapterViewPaper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hide on 11/03/2015.
 */
public class ChapterDetailFragment extends BaseFragment{
    public static final String TAG = ChapterDetailFragment.class.getName();
    private ChapterViewPaper chapterViewPaper = null;
    private ChapterPaperAdapter chapterPaperAdapter = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = null;
        try {
            root = inflater.inflate(R.layout.fragment_chapter_detail, container, false);


            chapterViewPaper = (ChapterViewPaper) root.findViewById(R.id.chapter_view_paper);
            if (chapterPaperAdapter == null) {
                chapterPaperAdapter = new ChapterPaperAdapter(getActivity());
            }
            chapterViewPaper.setAdapter(chapterPaperAdapter);
        } catch (Exception e) {
            e.printStackTrace();
            root = new View(getActivity());
        }
        return root;
    }

    @Override
    public void setData(ResponseDTO responseDTO) {
        super.setData(responseDTO);
        try{
            List<String> images = new ArrayList<>();
            Logger.i(TAG+".setData", responseDTO.response.toString());
            String str = (String)((JSONObject)responseDTO.response).getJSONObject(PostKeys.POST).get(PostKeys.CONTENT);
            str = str.substring(2, str.length()-2);
            str = str.replace("\\/","/");
            String[] array = str.split("\",\"");
            for(String a: array) {
                Logger.i(TAG + ".setData", a);
                images.add(a);
            }
            chapterPaperAdapter.setData(images);
        }catch (Exception e){
            e.printStackTrace();
            chapterPaperAdapter.setData(new ArrayList<String>());
        }
    }

    @Override
    public ResponseDTO getData() {
        return super.getData();
    }

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }
}
