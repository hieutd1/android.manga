package com.vnfapps.hide.manga.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.vnfapps.hide.manga.R;
import com.vnfapps.hide.manga.models.Post;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.Logger;
import com.vnfapps.hide.manga.views.adapters.ChapterListAdapter;
import com.vnfapps.hide.manga.views.holders.BaseViewHolder;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by hide on 10/03/2015.
 */
public class StoryDetailFragment extends BaseFragment{
    public final static String TAG = StoryDetailFragment.class.getName();
    private final int        DESCRIBLE_LENGTH_LIMIT = 500;
    private TextView name;
    private TextView author;
    private TextView categories;
    private TextView describle;
    private ListView chapters;
    private NetworkImageView featuredImg;
    private ChapterListAdapter chapterListAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = null;
        try {
            root = inflater.inflate(R.layout.fragment_story_detail,container,false);
            name = (TextView)root.findViewById(R.id.name);
            author = (TextView)root.findViewById(R.id.author);
            categories = (TextView)root.findViewById(R.id.categories);
            describle = (TextView)root.findViewById(R.id.describle);
            chapters = (ListView)root.findViewById(R.id.chapters);
            featuredImg = (NetworkImageView)root.findViewById(R.id.featured_img);
            if(chapterListAdapter==null){
                chapterListAdapter = new ChapterListAdapter(getActivity());
            }
            chapters.setAdapter(chapterListAdapter);
            chapters.setOnItemClickListener(new OnChapterItemClick());

        }catch (Exception e){
            e.printStackTrace();
            root = new View(getActivity());
        }
        return root;
    }

    public void setData(ResponseDTO responseDTO){
        super.setData(responseDTO);
        try {
            JSONObject story = (JSONObject) this.responseDTO.response;
            JSONObject storyPost = story.getJSONObject(PostKeys.POST);
            JSONArray storyCategories = story.getJSONArray(StoryKeys.CATEGORIES);

            String name = storyPost.getString(PostKeys.NAME);
            String author = story.getString(StoryKeys.AUTHOR);
            String featuredImg = story.getString(StoryKeys.FEATURED_IMG);
            String categories = "";
            String describle = storyPost.getString(PostKeys.CONTENT);

            if (describle.length() > DESCRIBLE_LENGTH_LIMIT) {
                int positionEnd = DESCRIBLE_LENGTH_LIMIT;
                for (int i = DESCRIBLE_LENGTH_LIMIT; i < describle.length(); i++) {
                    if (describle.charAt(i) == ' ') {
                        positionEnd = i;
                        break;
                    }
                }
                describle = describle.subSequence(0, positionEnd) + resources.getString(R.string.more);
            }

            for (int i = 0; i < storyCategories.length(); i++) {
                JSONObject object = storyCategories.getJSONObject(i);
                categories += object.getString(CategoryKeys.NAME) + ", ";
            }

            this.name.setText(name);
            this.author.setText(author);
            this.categories.setText(categories);
            this.describle.setText(describle);
            if (imageLoader != null) {
                this.featuredImg.setImageUrl(featuredImg, imageLoader);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        chapterListAdapter.setData(this.responseDTO);
    }


    private class OnChapterItemClick implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                JSONObject object = (JSONObject) parent.getAdapter().getItem(position);
                Logger.i(TAG + ".onItemClick", object.toString());
                BaseViewHolder.getInstance().getActivityAction().showChapterDetail(object.getString(PostKeys.SLUG), object.getInt(ChapterKeys.NO));
            }catch (Exception e){
            e.printStackTrace();
        }
        }
    }
}
