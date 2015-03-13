package com.vnfapps.hide.manga.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.vnfapps.hide.manga.R;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.Logger;
import com.vnfapps.hide.manga.views.adapters.StoryGridAdapter;
import com.vnfapps.hide.manga.views.holders.BaseViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;


public class StoryListFragment extends BaseFragment{
    public static final String TAG = StoryListFragment.class.getName();
    private GridView            gridView            = null;
    private EditText            editText            = null;
    private Button              clearQueryBtn          = null;
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
            editText = (EditText) root.findViewById(R.id.storySearch);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Logger.i(TAG + ".afterTextChanged", s.toString());
                    storyGridAdapter.setData(fillData(responseDTO, s.toString()));
                }
            });

            clearQueryBtn = (Button) root.findViewById(R.id.clear_text);
            clearQueryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText("");
                    storyGridAdapter.setData(responseDTO);
                }
            });

            if (storyGridAdapter == null){
                storyGridAdapter = new StoryGridAdapter(getActivity());
            }
            gridView.setAdapter(storyGridAdapter);
            gridView.setOnItemClickListener(new OnGridItemClick());

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

    private ResponseDTO fillData(ResponseDTO responseDTO, String searchQuerry){
        ResponseDTO filledData = new ResponseDTO();
        filledData.setData(responseDTO.toString());
        searchQuerry = searchQuerry.toLowerCase();
        try {
            JSONArray stories = (JSONArray) responseDTO.response;
            JSONArray filledStories = new JSONArray();
            for (int i = 0; i < stories.length(); i++) {
                if (stories.getJSONObject(i).getJSONObject(PostKeys.POST).getString(PostKeys.NAME).toLowerCase().contains(searchQuerry)){
                    filledStories.put(stories.getJSONObject(i));
                }
            }
            filledData.response = filledStories;
        }catch (Exception e){
            e.printStackTrace();
        }
        return filledData;
    }

    private class OnGridItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                JSONObject story = (JSONObject) parent.getAdapter().getItem(position);
                JSONObject post = story.getJSONObject(PostKeys.POST);
                Logger.i(TAG + ".onItemClick", story.toString());
                editText.setText("");
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                BaseViewHolder.getInstance().getActivityAction().showStoryDetail(1, -1, post.getString(PostKeys.SLUG));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
