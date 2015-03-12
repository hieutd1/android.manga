package com.vnfapps.hide.manga.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.vnfapps.hide.manga.R;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.models.Story;
import com.vnfapps.hide.manga.utils.Logger;
import com.vnfapps.hide.manga.views.adapters.StoryGridAdapter;
import com.vnfapps.hide.manga.views.holders.BaseViewHolder;

import org.json.JSONObject;


public class StoryListFragment extends BaseFragment{
    public static final String TAG = StoryListFragment.class.getName();
    private GridView            gridView            = null;
    private StoryGridAdapter    storyGridAdapter    = null;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = null;
        try {
            root = inflater.inflate(R.layout.fragment_story_list, container, false);
            gridView = (GridView) root.findViewById(R.id.gridView);
            if (storyGridAdapter == null){
                storyGridAdapter = new StoryGridAdapter(getActivity());
            }
            gridView.setAdapter(storyGridAdapter);
            gridView.setOnItemClickListener(new OnGridItemClick());
            //requestData(1, -1);

        }catch (Exception e){
            e.printStackTrace();
            root = new View(getActivity());
        }
        return root;
    }

    @Override
    public void setData(ResponseDTO responseDTO){
        Logger.d(TAG+".setData", "start");
        super.setData(responseDTO);
        if(storyGridAdapter!=null){
            storyGridAdapter.setData(this.responseDTO);
        }else {
            Logger.e(TAG + ".setData", "storyGridAdapter is null");
        }
    }

    private class OnGridItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                JSONObject story = (JSONObject) parent.getAdapter().getItem(position);
                JSONObject post = story.getJSONObject(PostKeys.POST);
                Logger.i(TAG + ".onItemClick", story.toString());
                BaseViewHolder.getInstance().getActivityAction().showStoryDetail(1, -1, post.getString(PostKeys.SLUG));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
