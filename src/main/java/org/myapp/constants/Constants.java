package org.myapp.constants;

public final class Constants {

    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";


    public static final int STATUS_OK = 200;
    public static final int STATUS_CREATED = 201;
    public static final int STATUS_NO_CONTENT = 204;
    public static final int STATUS_NOT_FOUND = 404;

    public static final String REG_POST_CREATE = "/.+/create";
    public static final String REG_POST_UPDATE = "/.+/update";
    public static final String REG_DELETE = "/.+/delete";
    public static final String REG_GET_SEARCH = "/.+/search";
    public static final String REG_ATTRIBUTES_DELIMITER = "&";
    public static final String REG_ATTRIBUTE_DELIMITER = "=";
    private Constants() {}
}
