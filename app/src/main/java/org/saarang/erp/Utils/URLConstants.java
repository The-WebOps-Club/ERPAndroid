package org.saarang.erp.Utils;

/**
 * Created by Ahammad on 16/06/15.
 */
public class URLConstants {

//      public static String SERVER = "http://erptest.saarang.org/";
    public static String SERVER = "http://192.168.0.195:9000/";

    public static String URL_LOGIN = "auth/local/mobile";
    public static String URL_FORGOT_PASSWORD = "api/users/forgotPassword";
    public static String URL_UPLOAD = "api/uploads";

    // the update link is like api/users/<id>/updateProfile

    public static String URL_UPDATE1="api/users";
    public static String URL_UPDATE2="updateProfile";

    public static String URL_REGISTER_DEVICE = SERVER + "api/users/gcmRegister";

    public static String URL_NEWSFEED_PAGE = SERVER + "api/posts/newsfeed/";
    public static String URL_NEWSFEED_REFRESH = SERVER + "api/posts/newsfeed/refresh";

    public static String URL_POST_ACKNOWLEDGE = SERVER + "api/posts/";
    public static String URL_POST_COMMENT_ADD = SERVER + "api/posts/addComment/";
    public static String URL_POST_NEW = SERVER + "api/posts/";



}
