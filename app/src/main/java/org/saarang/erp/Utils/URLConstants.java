package org.saarang.erp.Utils;

/**
 * Created by Ahammad on 16/06/15.
 */
public class URLConstants {

      public static String SERVER = "http://erptest.saarang.org/";
//    public static String SERVER = "http://10.21.211.179:9000/";
//    public static String SERVER = "http://10.42.0.77:9000/";

    public static String URL_LOGIN = "auth/local/mobile";
    public static String URL_FORGOT_PASSWORD = "api/users/forgotPassword";
    public static String URL_UPLOAD = "api/uploads";

    // the update link is like api/users/<id>/updateProfile

    public static String URL_UPDATE1="api/users";
    public static String URL_UPDATE2="updateProfile";

    public static String URL_REGISTER_DEVICE = SERVER + "api/users/gcmRegister";
}
