package in.codifi.Utility;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

public class AppConstants {

    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String PUT_METHOD = "PUT";
    public static final String DELETE_METHOD = "DELETE";
    public static final String UTF_8 = "utf-8";
    public static final String ACCEPT = "Accept";
    public static final String BEARER_WITH_SPACE = "Bearer ";

    public static final int RESPONSE_CODE = 200;
    public static final String SUCCESS_STATUS = "Success";
    public static final String FAILED_STATUS = "Failed";
    public static final String STATUS_OK = "Ok";
    public static final String STATUS_NOT_OK = "Not ok";
    public static final String FAILED_CODE = "400";
    public static final String SUCCESS_CODE = "200";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final List<JSONObject> EMPTY_ARRAY = new ArrayList<>();

    public static final String INVALID_PARAMETERS = "Invalid Parameters";
    public static final String INSERTED = "Inserted Successfully";
    public static final String NO_RECORDS_FOUND = "No Records Found";
    public static final String DELETED = "Deleted successfully";

    public static final String INVALID_PARAMETER = "Invalid Parameter";
    public static final String GUEST_USER_ERROR = "Guest User";
    public static final String ALL = "ALL";
    
    public static final String INVALID_OS = "Invalid OS";

    public static final String HAZEL_KEY_REST_SESSION = "_REST_SESSION";

    public static final String JDATA = "jData";
    public static final String JKEY = "jKey";
    public static final String SYMBOL_EQUAL = "=";
    public static final String SYMBOL_AND = "&";

    public static final String BASE_URL = "http://103.210.195.138/NestHtml5MobileTech/rest/";
    public static final String LIMITS = "Limits";
    public static final String JSESSONID = "jsessionid";

    public static final String REST_STATUS_SUCCESS = "success";
    public static final String REST_STATUS_TRUE = "true";
    public static final String REST_STATUS_NOT_OK = "not_ok";
    public static final String REST_STATUS_OK = "Ok";
    public static final String REST_NO_DATA = "no data";
    public static final String NO_RECORD_FOUND = "No records are found";
    public static final String NO_DATA_FOUND = "No data found";
    public static final String PRODUCT_TYPE = "Product Type";
    public static final String ORDER_TYPE = "Order Type";
    public static final String PRICE_TYPE = "Price Type";
    public static final String REST_STATUS_ERROR = "error";

    public static final String MODULE_COMMON = "Common";
    public static final String USER_AGENT = "User-Agent";

    public static final String COLON = ":";
    public static final String TYPE = "type";
    public static final String QUESTION_MARK = "?";
    public static final String INTROPSTATUS = "intropStatus";
    public static final String ONE = "1";

    public static final String X_API_KEY_NAME = "x-api-key";

    public static final String JTENANT_TOKEN_KEY = "jtenanttoken";
    public static final String JTENANT_TOKEN_VALUE = "1";

    public static final String JTENANT_ID = "jtenantid";
    public static final String JTENANT_ID_VALUE = "1404";

    public static final String CONTENT_LENGTH_KEY = "Content-Length";
    public static final String CONTENT_LENGTH_VALUE = "<calculated when request is sent>";

    public static final String HAZEL_KEY_INDICES = "INDICES";
    public static final int TWELVE_MILISEC = 1000;
    public static final int NINE_FIFTEN_MILISEC = 33300000;
    public static final int FIFTEN_THIRTY_MILISEC = 55800000;
    public static final int TWENTY_THREE_FIFTY_NINE_MILISEC = 86399000;

    public static final String HAZEL_KEY_VERSION = "version";

    public static final String UPDATED_TIME = "AnalysisUpdatedTime";
    public static final String INTERNAL_ERROR = "Something went wrong. Please try again later";
    public static final String HAZEL_KEY_EQSECTOR = "EQSECTOR";
    public static final String HAZEL_KEY_SECTORS = "SECTORS";
    public static final String INVALID_SEGMENT = "Invalid Segment";
    public static final String LOADED = "Loaded Successfully";
    public static final String VERSION_UPDATED = "Version Updated";
    public static final String COLOR_CODE_GREEN = "F6FDF4";
    public static final String COLOR_CODE_RED = "FFE0DD";
    
    public static final String HAZEL_KEY_SECTORS_NEW = "SECTORSNEW";
    
    public static final String NSE = "NSE";
    public static final String BSE = "BSE";
    public static final String NFO = "NFO";
    public static final String CDS = "CDS";
    public static final String NSE_EQ = "NSE_EQ";
    public static final String BSE_EQ = "BSE_EQ";
    public static final String NSE_FO = "NSE_FO";
    public static final String NSE_CUR = "NSE_CUR";
    public static final String INVALID_EXCH = "Invalid exchange";
    
    public static final String AUTHORIZATION_DII_FII = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6ImNob2xhbWFuZGFsYW1hcGlzIiwicm9sZSI6IkFkbWluIiwibmJmIjoxNzA2MTAwMTUyLCJleHAiOjE3MDg2OTIxNTIsImlhdCI6MTcwNjEwMDE1MiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo1MDE5MSIsImF1ZCI6Imh0dHA6Ly9sb2NhbGhvc3Q6NTAxOTEifQ.Kjmk2agRLRU7ZskgvtsgpRCg2dv_gy0g4jBOP2JE1As";
}