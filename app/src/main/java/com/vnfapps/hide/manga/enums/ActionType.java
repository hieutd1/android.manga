package com.vnfapps.hide.manga.enums;

public enum ActionType {
    /*
     * **********************************************************************
     * API Tag
     * **********************************************************************
     */
    DO_NOTHING(""),
    
    GET_CATEGORY_LIST("category.list"),
    
    GET_CATEGORY("category.show"),
    
    GET_CATEGORY_SEARCH("category.search.result"),
    
    GET_STORY_LIST("story.list"),
    
    GET_STORY_SEARCH("story.search.result"),
    
    GET_STORY("story.show"),
    
    GET_CHAPTER_NEWEST("chapter.newest.list"),
    
    GET_CHAPTER("chapter.show");
    
    /*
     * **********************************************************************
     * Variables
     * **********************************************************************
     */
    private String tag = null;
    
    /*
     * **********************************************************************
     * Function
     * **********************************************************************
     */
    private ActionType(String tag) {
        this.tag = tag;
    }
    
    public String getType() {
        return tag;
    }
}
