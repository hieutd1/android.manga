package com.vnfapps.hide.manga;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;

import com.vnfapps.hide.manga.controllers.RequestController;
import com.vnfapps.hide.manga.enums.ActionType;
import com.vnfapps.hide.manga.enums.URL;
import com.vnfapps.hide.manga.interfaces.ActivityAction;
import com.vnfapps.hide.manga.interfaces.RequestListener;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.views.fragments.BaseFragment;
import com.vnfapps.hide.manga.views.fragments.StoryDetailFragment;
import com.vnfapps.hide.manga.views.fragments.StoryListFragment;
import com.vnfapps.hide.manga.views.holders.BaseViewHolder;
import com.vnfapps.hide.manga.utils.Logger;

import org.json.JSONObject;


public class MainActivity extends FragmentActivity implements RequestListener, ActivityAction {
    private final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.activity_main, null, false);
            setContentView(view);
            BaseViewHolder.init(R.layout.activity_main, view, this, getSupportFragmentManager());
            RequestController.init(this, this);
            showStoryList(1, -1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void fragmentsInit() throws Exception {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Logger.i(TAG + ".fragmentInit", fragmentManager.toString());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BaseFragment currentFragment = new StoryListFragment();
        if (!currentFragment.isAdded()){
            fragmentTransaction.add(R.id.content, currentFragment, StoryListFragment.TAG).hide(currentFragment);
        }else{
            fragmentTransaction.replace(R.id.content,currentFragment,StoryListFragment.TAG).hide(currentFragment);
        }
        currentFragment = new StoryDetailFragment();
        if (!currentFragment.isAdded()){
            fragmentTransaction.add(R.id.content, currentFragment, StoryDetailFragment.TAG).hide(currentFragment);
        }else{
            fragmentTransaction.replace(R.id.content, currentFragment, StoryDetailFragment.TAG).hide(currentFragment);
        }
        //fragmentTransaction.addToBackStack("initFragment");
        fragmentTransaction.addToBackStack("initFragment").commit();
        if(fragmentManager.executePendingTransactions()) {
            Logger.i(TAG + ".fragmentInit", Integer.toString(fragmentManager.getFragments().size()));
        }

    }
    @Override
    public void onRequest() throws Exception {
        BaseViewHolder.getInstance().showProgress(true);
    }

    @Override
    public void onResponse(ActionType actionType,final ResponseDTO responseDTO) throws Exception {
        Logger.i(TAG+".onResponse", actionType.getType());
        BaseViewHolder.getInstance().setData(responseDTO);
    }

    @Override
    public void onError() throws Exception {

    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        /*if(!BaseViewHolder.getInstance().onBackPressed()) {
            super.onBackPressed();
        }*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        /*FragmentManager.BackStackEntry backEntry=fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1);
        Logger.i(TAG+"onBackPressed", backEntry.toString());*/
        //Logger.i(TAG+"onBackPressed", Integer.toString(BaseViewHolder.getInstance().onBackPressed()));
        fragmentManager.popBackStack();
        fragmentManager.executePendingTransactions();
        if(fragmentManager.getBackStackEntryCount()==0){
            super.onBackPressed();
        }else {
            BaseViewHolder.getInstance().onBackPressed();
        }
    }

    @Override
    public void showStoryList(int page, int perPage) throws Exception{
        BaseViewHolder.getInstance().showStoryList();
        RequestController.getInstance().request(ActionType.GET_STORY_LIST, URL.STORY_LIST, page, perPage);
    }

    @Override
    public void showStoryDetail(int page, int perPage, String slug) throws Exception{
        BaseViewHolder.getInstance().showStoryDetail();
        RequestController.getInstance().request(ActionType.GET_STORY, URL.STORY_DETAIL, page, perPage, slug);
    }

    @Override
    public void showChapterDetail(String slug, int no) throws Exception{
        BaseViewHolder.getInstance().showChapterDetail();
        RequestController.getInstance().request(ActionType.GET_CHAPTER, URL.CHAPTER_DETAIL, slug, no);
    }

    @Override
    public void showNewestChapter(int page, int perPage) throws Exception {

    }
}
