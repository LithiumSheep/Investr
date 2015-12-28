package group15.oose.investmenttinder.helpers;

/**
 * Created by adegokeolubusi on 10/24/15.
 * Simple file keeps all needed fields for the application like fragment tags
 */
public class AppConst {

    public static final String HOME_ACTION = "group15.oose.investmenttinder.HOME_ACTION";
    public static final String STOCKS_ACTION = "group15.oose.investmenttinder.STOCKS_ACTION";
    public static final String PREFERENCES_ACTION = "group15.oose.investmenttinder.PREFERENCES_ACTION";
    public static final String PROFILE_ACTION = "group15.oose.investmenttinder.PROFILE_ACTION";
    public static final String LOGOUT_ACTION = "group15.oose.investmenttinder.LOGOUT_ACTION";
    public static final String DETAILS_ACTION = "group15.oose.investmenttinder.DETAILS_ACTION";

    //Change this address to your broadcasted IP
    //public final static String API_BASE_URL = "http://10.0.0.92:8080/api/v1/";


    /*this is a dev address but while hosted will broadcast to the internet
    if using a local address does not work or you are having port forwarding troubles, contact jesse.elite@gmail.com and I will
    host an instance of the server for a while on this address.*/
    public final static String API_BASE_URL = "http://73.201.247.124:8080/api/v1/";
}

