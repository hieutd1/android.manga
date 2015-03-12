package com.vnfapps.hide.manga.views.customviews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.ortiz.touch.TouchImageView;

/**
 * Created by hide on 11/03/2015.
 */
public class ChapterViewPaper extends ViewPager {
    public ChapterViewPaper(Context context) {
        super(context);
    }

    public ChapterViewPaper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Tests scrollability within child views of v given a delta of dx.
     *
     * @param v      View to test for horizontal scrollability
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),
     *               or just its children (false).
     * @param dx     Delta scrolled in pixels
     * @param x      X coordinate of the active touch point
     * @param y      Y coordinate of the active touch point
     * @return true if child views of v can be scrolled by delta of dx.
     */
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if(v instanceof TouchImageView) {
            return ((TouchImageView) v).canScrollHorizontallyFroyo(-dx);
        }
        return super.canScroll(v, checkV, dx, x, y);
    }
}
