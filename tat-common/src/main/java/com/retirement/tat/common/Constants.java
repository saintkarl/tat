package com.retirement.tat.common;

public class Constants {
    /**
     * Sort direction constants
     */
    public static final String SORT_ASC = "2";
    public static final String SORT_DESC = "1";


    //~ Static fields/initializers =============================================

    public static final String DATE_FORMAT = "MM/dd/yyyy";
    public static final String DATE_FORMAT_VN = "dd/MM/yyyy";

    public static final String MONTH_YEAR_FORMAT = "MM-yyyy";
    /**
     * The Alphabet number for search query
     */
    public static final String ALPHABET_SEARCH_PREFIX = "^$^";
    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute that holds the list form
     */
    public static final String LIST_MODEL_KEY = "items";

    /**
     * The request scope attribute that holds the form
     */
    public static final String FORM_MODEL_KEY = "item";


    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ADMIN";


    /**
     * The name of the user's role list, common.a request-scoped attribute
     * when adding/editing common.a user.
     */
    public static final String USER_ROLES = "userRoles";

    public static final String TOS_ROLES = "TOSRoles";

    /**
     * The name of the available roles list, common.a request-scoped attribute
     * when adding/editing common.a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     */
    public static final String CSS_THEME = "csstheme";


    /**
     * Acegi security constants
     */

    public static final String ACEGI_SECURITY_FORM_USERNAME_KEY = "j_username";
    public static final String ACEGI_SECURITY_FORM_PASSWORD_KEY = "j_password";
    public static final String ACEGI_SECURITY_LAST_USERNAME_KEY = "ACEGI_SECURITY_LAST_USERNAME";

    public static final String ACEGI_SECURITY_COOKIE_CURL_NAME_KEY = "i_curlName";
    public static final String ACEGI_SECURITY_COOKIE_TYPE_KEY = "i_type";


    /**
     * Cookie for web and content security
     */
    public static final String LOGIN_USER_ID_COOKIE = "j_loggined_userid";
    public static final String LOGIN_CHECKSUM = "j_loginned_checksum";
    public static final String CHECKSUM_SECURITY_HASH = "iBanVienConnect";
    public static final String LOGIN_SCHOOL_ID_COOKIE = "j_school_id";
    public static final String LOGIN_ROLE_COOKIE = "j_role";

    public static final String LOGON = "LOGON";

    public static final String PASSCODE_VALIDATION_FLAG = "passcodeValidation";
    public static final String PASSCODE_FLAG = "loginPasscode";

    /**
     * The login user type: School/District or Administrator
     */
    public static final int LOGIN_AS_ADMINISTRATOR = 0;
    public static final int LOGIN_AS_SCHOOL = 1;
    public static final int LOGIN_AS_DISTRICT = 2;
    public static final int LOGIN_AS_DISTRICT_BY_LMS_ID = 3;
    public static final int LOGIN_AS_SCHOOL_BY_LMS_ID = 4;

    /**
     * User status constants
     */
    public static final long USER_ACTIVE = 1;
    public static final long USER_INACTIVE = 2;
    public static final long USER_DISABLED = 3;

    /**
     * Student scope
     */
    public static final Integer ALL_STUDENTS = 0;
    public static final Integer IN_CLASS = 1;
    public static final Integer NOT_IN_CLASS = 2;

    /**
     * Action Request
     */
    public static final String LOAD = "load";
    public static final String SAVE = "save";
    public static final String EDIT = "edit";
    public static final String UPLOAD = "upload";
    public static final String LIST = "list";

    /**
     * Global meta data
     */
    public static final String GLOBAL_META_ROLE_PREFIX = "GL_META_ROLE_";
    public static final String ACEGI_SECURITY_ORG_CODE = "ACEGI_SECURITY_ORG_CODE";
    public static final String ACEGI_SECURITY_ORG_KEY = "ACEGI_SECURITY_ORG_KEY";
    public static final String ACEGI_REMEMBER_ME_COOKIE_KEY = "BanVienConnectRememberMeKey";
    public static final String SECURITY_CREDENTIAL_DELIMITER = "SECURITY_CREDENTIAL_DELIMITER";
    public static final String ACEGI_SECURITY_FORM_ORG_KEY = "ACEGI_SECURITY_FORM_ORG_KEY";
    public static final String ORG_CODE = "ORG_CODE";
    public static final String ORG_ID = "ORG_ID";
    public static final String ORG_SESSION = "ORG_SESSION";
    public static final String LOGON_DEFAULT_PERMISSION = "LOGON";
    public static final String PLATFORM_CACHE_JNDI_URI = "java:jboss/infinispan/container/ersPlatform";


    public static final String INSERT_OR_UPDATE = "insert-update";
    public static final String ACTION_DELETE = "delete";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String MESSAGE_RESPONSE = "messageResponse";

    public static final String USERNAME = "username";
    public static final String CODE = "code";
    public static final String EMAIL = "email";

    public static final String JCR_SEPARATOR = "/";
    public static final String image_path = "/jackrabbit/repository/default";
    public static final String CONFIRM_WORKING_IMAGE = "/working";
    public static final String IMAGE_OUTLET = "/outlet";
    public static final String IMAGE_QUESTION_PATH = "/survey/question";

    public static final String OUTLET_TASKLIST_ACTION = "com.banvien.fcv.mobile.OUTLET_TASKLIST";
    public static final String POSM_LIST = "com.banvien.fcv.mobile.POSM_LIST";
    public static final String HOTZONE_LIST = "com.banvien.fcv.mobile.HOTZONE_LIST";
    public static final String CATGROUP_LIST = "com.banvien.fcv.mobile.CATGROUP_LIST";
    public static final String PRODUCTGROUP_LIST = "com.banvien.fcv.mobile.PRODUCTGROUP_LIST";
    public static final String PRODUCT_LIST = "com.banvien.fcv.mobile.PRODUCT_LIST";
    public static final String ROUTESCHEDULE_LIST = "com.banvien.fcv.mobile.ROUTESCHEDULE_LIST";
    public static final String COMPLAINTYPE_LIST = "com.banvien.fcv.mobile.COMPLAINTYPE_LIST";

    public static final int OUTLET_ID = 0;
    public static final int CATGROUP_ID = 1;
    public static final int QUANTITY = 2;
    public static final int PRICE = 3;
    public static final int MONTH = 4;
    public static final int YEAR = 5;
    public static final int ACTION = 6;
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final int COLUMN_NUM = 7;

    //approve gift transfer
    public static final int GIFT_PENDING = 0;
    public static final int GIFT_APPROVE = 1;
    public static final int GIFT_DECLINE = -1;

    //change retailer account
    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int LOYALTY_CUSTOMER = 2;


    public static final String REPOSITORY_FOLDER = "fcv";
    public static final String MER_IMAGE = "IMAGE";
    public static final Boolean isRetailer = true;

    public static final String SUGGEST_KPI = "Suggest";

    //exhibit register excel column
    public static final int EX_COLUMN_NUM = 15;
    public static final int EX_OUTLET_ID = 0;
    public static final int EX_MONTH = 1;
    public static final int EX_YEAR = 2;
    public static final int EX_OUTLET_MODEL_ID = 3;
    public static final int EX_OUTLET_KPI_ID = 4;
    public static final int EX_TYPE = 5;
    public static final int EX_DATA_TYPE = 7;

    public static final int EX_RULE = 6;
    public static final int EX_PRIORITY = 8;
    public static final int EX_WEIGHT = 9;
    public static final int EX_INPUT_REQUIRED = 10;
    public static final int EX_MULTI_VALUE = 11;
    public static final int EX_DOMAIN_VALUE = 12;
    public static final int EX_REGISTERED_VALUE = 13;
    public static final int EX_ACTION = 14;
    public static final String BOOLEAN = "B";
    public static final String NUMBER = "N";
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String EX_INSERT = "I";
    public static final String EX_UPDATE = "U";
    public static final String EX_DELETE = "D";

    public static final String TYPE = "type";
    public static final String COMPLAINTYPE = "COMPLAINTYPE";
    public static final String COMPLAIN = "COMPLAIN";

    public static final String KPI_ITEM_TYPE = "kpi";
    public static final String OUTLET_MODEL_ITEM_TYPE = "outlet_model";
    public static final Short minYear = 1970;

    /*Excel import constants*/
    public static final String IMPORT_OUTLET_CODE = "FCV_UNIQUECODE";
    public static final String IMPORT_DMS_CODE = "MÃ DMS";
    public static final String IMPORT_OUTLET_TYPE = "Loại CH";
    public static final String IMPORT_REGION = "VÙNG";
    public static final String IMPORT_DISTRIBUTOR_CODE = "MÃ NPP";
    public static final String IMPORT_DISTRIBUTOR_NAME = "TÊN NPP";
    public static final String IMPORT_TELEPHONE = "SỐ ĐIỆN THOẠI";
    public static final String IMPORT_OUTLET_NAME = "TÊN CỬA HIỆU";
    public static final String IMPORT_OUTLET_OWNER = "TÊN CHỦ CH";
    public static final String IMPORT_STREET_NUMBER = "SỐ NHÀ/TÊN CHỦ CH";
    public static final String IMPORT_STREET_VILLAGE = "TÊN ĐƯỜNG/ THÔN";
    public static final String IMPORT_WARDS = "PHƯỜNG/XÃ";
    public static final String IMPORT_DISTRICT = "QUẬN/HUYỆN";
    public static final String IMPORT_CITY = "THÀNH PHỐ/TỈNH";
    public static final String IMPORT_LATITUDE = "Lat";
    public static final String IMPORT_LONGTITUDE = "Long";

    /*Mer constant*/
    public static final String BEFORE = "BEFORE";
    public static final String AFTER = "AFTER";
    public static final String EIE_BEFORE = "EIE_BEFORE";
    public static final String EIE_AFTER = "EIE_AFTER";
    public static final String MHS = "MHS";
    public static final String MHS_BEFORE = "MHS_BEFORE";
    public static final String MHS_AFTER = "MHS_AFTER";
    public static final String HOTZONE = "HOTZONE";
    public static final String HOTZONE_BEFORE = "HOTZONE_BEFORE";
    public static final String HOTZONE_AFTER = "HOTZONE_AFTER";
    public static final String FACING_TITLE = "FACING";
    public static final String FACING_BEFORE = "FACING_BEFORE";
    public static final String FACING_AFTER = "FACING_AFTER";
    public static final String POSM = "POSM";
    public static final String IMPORT = "import";

    public static final String HOTZONE_POSITION = "1/2/3/4";
    public static final String NK = "6/12/24";
    public static final String FACING = "1";
    public static final String ZERO = "0";
    //    public static String FACING_DL_IFT = "3";
    public static final int FCVUNIQECODE = 1;
    public static final String HZ1 = "1";
    public static final String HZ2 = "2";
    public static final String HZ3 = "3";
    public static final String HZ4 = "4";
    public static final String EMPTY = "<i class=\"zmdi zmdi-alert-circle-o\"></i>";

    public static final int STT = 0;
    public static final String Nguyen_kem = "03";
    public static final String Sua_nuoc = "02";
    public static final String Update = "Update:";
    public static final String ufriso = "Ụ Friso";
    public static final String uDLIFT = "Ụ DL IFT";
    public static final String TBUKE = "Vị trí trưng bày Ụ/Kệ DBB";
    public static final String TB_SUADAC = "Vị trí trưng bày Kệ sữa đặc";
    public static final String TB_F4K = "Vị trí trưng bày Kệ vui nhộn cho trẻ";
    public static final String TB_FRISO = "Vị trí trưng bày Friso";
    //    public static String TB_DL_IFT = "Vị trí trưng bày DL IFT";
    public static final String TB_NK = "Vị trí trưng bày nguyên kem";
    public static final String FRISO = "FRISO";
    public static final String FRISO_DLIFT = "FRISO_DLIFT";
    public static final String U_DBB = "010201";
    public static final String CHDD = "CHDD";
    public static final String KE_DBB = "010301";
    public static final String U_DBB_CH = "010103";
    public static final String KE_DBB_CH = "010302";
    public static final String U_CHUAN_KE = "010202";
    public static final String KE_SCM = "010401";
    public static final String KE_SCM_CH = "010402";
    public static final String FUN_4_KIDS = "010403";
//    public static final String U_FRISO = "U_FRISO";
 //   public static final String U_DLFRISO = "U_DLFRISO";
    public static final String U_CHINH_DIEN_FRISO = "020104";
    public static final String U_CHINH_DIEN_DLIFT = "020105";
    public static final String COMMA_DELIMITER = ",";
    public static final String COLON_DELIMITER = ":";
    public static final String EIE_TOTAL = "Total";
    public static final String EIE_STATUS = "Status";
    public static final int EIE_PASSED = 1;
    public static final int EIE_FAILED = 0;
    public static final int EIE_ERROR = -1;
    public static final int NEGATIVE_THREE_MONTH = -3;
    public static final int OUTLET_SERVED_STATUS = 1;
    public static final int OUTLET_ALL_STATUS = -1;

    public static final String ROLE_EDIT = "role";
    public static final String INFO_EDIT = "info";
    public static final String USER_LINE_EDIT = "user-line";
    public static final Integer USER_LINE_ACTIVE = 1;
    public static final Integer USER_LINE_INACTIVE = 0;

    public static final String MOBILE_JWT_CACHE_PREFIX = "m_jwt_";

    public static final String MOBILE_JWT_CLAIM_USER_NAME = "userName";
    public static final String MOBILE_JWT_CLAIM_USER_ID = "userId";
    public static final String MOBILE_JWT_CLAIM_USER_ROLE = "role";

    public static final int MOBILE_PRINCIPAL_CACHE_EXPIRE_TIME_SECS = 7200; // 120'
}
