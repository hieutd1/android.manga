package com.vnfapps.hide.manga.views.holders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.vnfapps.hide.manga.R;
import com.vnfapps.hide.manga.controllers.RequestController;
import com.vnfapps.hide.manga.enums.ActionType;
import com.vnfapps.hide.manga.interfaces.ActivityAction;
import com.vnfapps.hide.manga.models.ResponseDTO;
import com.vnfapps.hide.manga.utils.Logger;
import com.vnfapps.hide.manga.views.fragments.BaseFragment;
import com.vnfapps.hide.manga.views.fragments.ChapterDetailFragment;
import com.vnfapps.hide.manga.views.fragments.StoryDetailFragment;
import com.vnfapps.hide.manga.views.fragments.StoryListFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by hide on 09/03/2015.
 */
public class BaseViewHolder {
    public static final String     TAG             = BaseViewHolder.class.getName();
    protected final static String FRAGMENTS_INIT_TAG = "fragmentsInit";

    protected static final int        FRAGMENT_CONTAINER   = R.id.content;

    protected static BaseViewHolder instance;
    private HashMap<Integer, View> viewRoots;
    private ActivityAction activityAction;
    private BaseFragment currentFragment;
    private FragmentManager fragmentManager;

    public ActivityAction getActivityAction() {
        return activityAction;
    }

    protected BaseViewHolder(int layout, View view, ActivityAction activityAction, FragmentManager fragmentManager){
        if (viewRoots == null){
            viewRoots = new HashMap<>();
        }
        //save mainActivity view
        viewRoots.put(layout, view);
        this.fragmentManager = fragmentManager;
        this.activityAction = activityAction;
        showProgress(true);
        try {
            fragmentsInit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
     ************************************************************
     * Singleton functions
     ************************************************************
     */

    public synchronized static void init(int layout, View view, ActivityAction activityAction, FragmentManager fragmentManager){
        instance = new BaseViewHolder(layout, view, activityAction, fragmentManager);
    }

    public synchronized static BaseViewHolder getInstance(){
        return instance;
    }

    /*
     ************************************************************
     * Fragment manage functions
     ************************************************************
     */
    public void fragmentsInit() throws Exception{
        Logger.i(TAG + ".fragmentInit", fragmentManager.toString());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        BaseFragment currentFragment = new StoryListFragment();
        fragmentTransaction.add(FRAGMENT_CONTAINER, currentFragment, StoryListFragment.TAG).hide(currentFragment);

        currentFragment = new StoryDetailFragment();
        fragmentTransaction.add(FRAGMENT_CONTAINER, currentFragment, StoryDetailFragment.TAG).hide(currentFragment);

        currentFragment = new ChapterDetailFragment();
        fragmentTransaction.add(FRAGMENT_CONTAINER, currentFragment, ChapterDetailFragment.TAG).hide(currentFragment);
        fragmentTransaction.commit();

        currentFragment = null;
        if(fragmentManager.executePendingTransactions()) {
            Logger.i(TAG + ".fragmentInit", Integer.toString(fragmentManager.getFragments().size()));
        }

    }

    private void showFragment(String fragmentTag, String stepTag){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(currentFragment!=null){
            Logger.i(TAG + ".showFragment", currentFragment.toString());
            fragmentTransaction.hide(currentFragment);
        }else {
            Logger.i(TAG + ".showFragment", "currentFragment null");
        }
        currentFragment = (BaseFragment)fragmentManager.findFragmentByTag(fragmentTag);
        fragmentTransaction.show(currentFragment);
        fragmentTransaction.addToBackStack(fragmentTag).commit();
    }

    public void showProgress(boolean flag){
        ProgressBar progressBar = (ProgressBar)viewRoots.get(R.layout.activity_main).findViewById(R.id.progressBar);
        if(flag){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }


    public void showStoryList(){
        showFragment(StoryListFragment.TAG,ActionType.GET_STORY_LIST.getType());
    }

    public void showStoryDetail(){
        showFragment(StoryDetailFragment.TAG,ActionType.GET_STORY.getType());
    }

    public void showChapterDetail(){
        showFragment(ChapterDetailFragment.TAG, ActionType.GET_CHAPTER.getType());
    }
    public void onBackPressed(){
            FragmentManager.BackStackEntry backEntry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            Logger.d(TAG+".onBackPressed", backEntry.getName());
            currentFragment = (BaseFragment)fragmentManager.findFragmentByTag(backEntry.getName());
    }
    public void setData(ResponseDTO responseDTO) throws Exception{
        Logger.d(TAG+".setData", "start");
        if(currentFragment != null){
            Logger.d(TAG+".setData", currentFragment.toString());
            if(currentFragment instanceof StoryListFragment) {
                ((StoryListFragment) currentFragment).setData(responseDTO);
            }else if(currentFragment instanceof StoryDetailFragment){
                ((StoryDetailFragment) currentFragment).setData(responseDTO);
            }else if(currentFragment instanceof ChapterDetailFragment){
                ((ChapterDetailFragment) currentFragment).setData(responseDTO);
            }
        }

        showProgress(false);
    }



}
