package org.myapp.controller;


/**
 * This class provides constants for controllers
 */
final class Constants {
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";

    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_NO_CONTENT = 204;
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_NOT_FOUND = 404;

    public static final String REG_COMMON_ENDPOINT = "/.+/";
    public static final String REG_ATTRIBUTES_DELIMITER = "&";
    public static final String REG_ATTRIBUTE_DELIMITER = "=";

    public static final String REG_DIGIT = "^(0|[1-9][0-9]*)$";
    public static final String REG_NOT_BLANK = "[a-zA-Z]{2,50}";

    private Constants() {
    }
}
