package com.vnfapps.hide.manga.enums;

/**
 * Created by hide on 05/03/2015.
 */
interface URLParams{
    String PAGINATION_PARAMS  = "page=%s&per-page=%s";
    public static int DEFAULT_PER_PAGE    = 10;
    public static int DEFAULT_PAGE = 1;
}
public enum URL implements URLParams{
    NOTHING(""),
    CATEGORY_LIST("/category/list?" + PAGINATION_PARAMS),
    CATEGORY_DETAIL("/category?" + PAGINATION_PARAMS + "&id=%s"),
    CATEGORY_SEARCH("/category/search?" + PAGINATION_PARAMS + "&query=%s"),
    STORY_LIST("/story/list?" + PAGINATION_PARAMS),
    STORY_DETAIL("/story?" + PAGINATION_PARAMS + "&slug=%s"),
    STORY_SEARCH("/story/search?" + PAGINATION_PARAMS + "&query=%s"),
    CHAPTER_DETAIL("/chapter?&slug=%s&no=%s"),
    CHAPTER_NEWEST("/chapter/newest?" + PAGINATION_PARAMS);

    private static String HOST = "http://130.211.253.121/ajax";
    public String curURL;

    URL(String curURL) {
        this.curURL = curURL;
    }

    public String getCurURL() {
        return URL.HOST + curURL;
    }

}
