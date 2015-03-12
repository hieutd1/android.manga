package com.vnfapps.hide.manga.interfaces;


import android.support.v4.app.Fragment;

import com.vnfapps.hide.manga.views.fragments.BaseFragment;

/**
 * Created by hide on 09/03/2015.
 */
public interface ActivityAction {

    public void showStoryList(int page, int perPage) throws Exception;

    public void showStoryDetail(int page, int perPage, String slug) throws Exception;

    public void showChapterDetail(String slug, int no) throws Exception;

    public void showNewestChapter(int page, int perPage) throws Exception;
}
