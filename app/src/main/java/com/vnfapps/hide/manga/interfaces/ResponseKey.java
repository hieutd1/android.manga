package com.vnfapps.hide.manga.interfaces;

/**
 * Created by hide on 09/03/2015.
 */
public interface ResponseKey {
    public interface Key {
        public final String RESPONSE_CODE       = "code";
        public final String RESPONSE_MESSAGE    = "message";
        public final String RESPONSE_TAG        = "tag";
        public final String RESPONSE_RESULT     = "response";
    }
    public interface StoryKeys {
        public final String ID                  = "id";
        public final String FEATURED_IMG        = "featured_img";
        public final String AUTHOR              = "author";
        public final String CHAPTERS            = "chapters";
        public final String CATEGORIES          = "categories";
    }
    public interface PostKeys {
        public final String POST                = "post";
        public final String NAME                = "name";
        public final String SLUG                = "slug";
        public final String CREATED_AT          = "created_at";
        public final String CONTENT             = "content";
    }
    public interface ChapterKeys {
        public final String ID                  = "id";
        public final String NO                  = "no";

    }
    public interface CategoryKeys {
        public final String ID                  = "id";
        public final String NAME                = "name";

    }
}
