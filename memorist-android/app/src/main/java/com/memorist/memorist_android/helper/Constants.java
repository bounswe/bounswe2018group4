package com.memorist.memorist_android.helper;

/**
 * All constants are stored in this class.
 */
public class Constants {

    public static final String API_BASE_URL = "http://18.234.162.48:8000";
    public static final String API_LOGIN = API_BASE_URL + "/auth/login/";
    public static final String API_REGISTER = API_BASE_URL + "/auth/register/";
    public static final String API_CREATE_MEMORY = API_BASE_URL + "/post/create1/";
    public static final String API_CREATE_PHOTO = API_BASE_URL + "/post/media/1/";
    public static final String API_CREATE_VIDEO = API_BASE_URL + "/post/media/2/";
    public static final String API_CREATE_AUDIO = API_BASE_URL + "/post/media/3/";
    public static final String API_GET_MEMORY = API_BASE_URL + "/post/homepage/";
    public static final String API_GET_PROFILE = API_BASE_URL + "/auth/get_profile/";
    public static final String API_POST_LIKE = API_BASE_URL + "/post/like_post/";
    public static final String API_POST_DISLIKE = API_BASE_URL + "/post/dislike/";
    public static final String API_PROFILE_MEMORY_LIST = API_BASE_URL + "/post/list/";
    public static final String API_GET_FOLLEWERS = API_BASE_URL + "/auth/get_followers/";
    public static final String API_GET_FOLLOWINGS = API_BASE_URL + "/auth/get_followings/";
    public static final String API_EDIT_PROFILE = API_BASE_URL + "/auth/profile_update/";
    public static final String API_PHOTO_UPDATE = API_BASE_URL + "/auth/profile_photo/";
    public static final String API_CREATE_COMMENT = API_BASE_URL + "/post/create_comment/";

}