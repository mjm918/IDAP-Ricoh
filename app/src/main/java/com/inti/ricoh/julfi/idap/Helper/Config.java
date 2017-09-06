package com.inti.ricoh.julfi.idap.Helper;

/**
 * Created by julfi on 18/07/2017.
 */

public class Config {

//    Tracking request
    public static final String inSUBMISSION = "0";
    public static final String inOAR = "1";
    public static final String REQ_ELIGBLE = "-1";
    public static final String REQ_WITHDRAW = "withdraw";
    public static final String REQ_SSD = "ssd";
    public static final String REQ_TRANSCRIPT = "transcript";
//    DB
    static final String TABLE_NAME = "info";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_MAJOR = "major";
    static final String COLUMN_IC = "ic";
    static final String COLUMN_ID = "sid";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_STYPE = "stype";
    static final String COLUMN_MOBILE = "mobile";
    static final String COLUMN_BNAME = "bname";
    static final String COLUMN_BACC = "bacc";
    static final String COLUMN_BHOLDER = "bholder";
    static final String COLUMN_CITIZEN = "citizen";
    static final String COLUMN_DOB = "dob";
    static final String COLUMN_ADDRESS = "address";
//    api links
    public static final String API_LOGIN = "https://julfikarmahmud.000webhostapp.com/MobileApi/login.php";
    public static final String API_STUDENT_INFO = "https://julfikarmahmud.000webhostapp.com/MobileApi/info.php";
    public static final String API_WITHDRAW_INSERT = "https://julfikarmahmud.000webhostapp.com/MobileApi/withdrawReq.php";
    public static final String API_TRACK = "https://julfikarmahmud.000webhostapp.com/MobileApi/track.php";
    public static final String API_TRACK_PROGRESS = "https://julfikarmahmud.000webhostapp.com/MobileApi/trackProgress.php";
    public static final String API_EMP_NOTIFICATION = "https://julfikarmahmud.000webhostapp.com/MobileApi/notification.php";
    public static final String API_TOKEN_REG = "https://julfikarmahmud.000webhostapp.com/MobileApi/registerDevice.php";
//    shared preference
    public static final String SHARED_PREFERENCE = "com.inti.ricoh.julfi.idap.Helper";
    public static final String SP_KEY = "USER_ID";
    public static final String SP_STATUS = "STATUS";
    public static final String SP_UPDATED = "UPDATED";
    public static final String SP_VALID = "1";
    public static final String SP_INVALID = "0";
//    login status
    public static final String API_isStudent = "1";
    public static final String API_isStaff = "2";
//    api response
    public static final String API_INVALID = "0";
    public static final String API_VALID = "1";
    public static final String API_ERROR = "3";
    public static final String API_FAIL = "2";
//    info tags
    public static final String INFO_NOTIFICATION = "notification";
    public static final String INFO_ID = "id";
    public static final String INFO_STATUS = "status";
    public static final String INFO_DATE = "date";
    public static final String INFO_TAG = "info";
    public static final String INFO_MAJOR = "major";
    public static final String INFO_IC = "ic";
    public static final String INFO_BNAME = "bname";
    public static final String INFO_BACC = "bacc";
    public static final String INFO_BHOLDER = "bholder";
    public static final String INFO_MATRI = "matri";
    public static final String INFO_NAME = "name";
    public static final String INFO_DOB = "dob";
    public static final String INFO_STYPE = "stype";
    public static final String INFO_CITIZEN = "citizen";
    public static final String INFO_EMAIL = "email";
    public static final String INFO_MOBILE = "mobile";
    public static final String INFO_STREET = "street";
    public static final String INFO_CITY = "city";
    public static final String INFO_STATE = "state";
    public static final String INFO_COUNTRY = "country";
}
