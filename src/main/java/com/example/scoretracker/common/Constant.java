package com.example.scoretracker.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Constant {

    public static class DATE_FORMAT {
        public static final String yyyy_MM_dd = "yyyy-MM-dd";

        public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    }

    public static class STATUS {
        public static final int SUCCESS = 1;
        public static final int ERROR = 0;
    }

    public static class MESSAGE {
        public static final String SUCCESS = "Success";

        public static final String ERROR = "Error";
    }

    public static class PATTERN {
        public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    }

    public static final List<String> LIST_STATUS_PAGE_BUILDER = new ArrayList<>(Arrays.asList(
            "CONTINUE", "PAUSE", "TERMINATE"
    ));

    public static final List<String> LIST_STATUS_SSL = new ArrayList<>(Arrays.asList(
            "GENERATE", "REMOVE"
    ));

    public static final String HTML_STRUCTURE = "<!DOCTYPE html><html><head>${header}</head><body>${body}</body></html>";

    public static final String HEADER_NAME_TO_GEN_KEY = "Access-GenKey-Token";

    public static final Integer MAX_LENGTH_DOMAIN = 255;

    public static final String ENV_ACCESS_GEN_KEY_TOKEN = "ACCESS_GEN_KEY_TOKEN"; // ACCESS_GEN_KEY_TOKEN

    public static final String ENV_SECRET_KEY_API_PAGE_BUILDER = "SECRET_KEY_API_PAGE_BUILDER";

    public static final String LANDING_COPEN_DOMAIN = "https://landing.copen.vn";

    public static final String PT_PATH_TEMPLATE_PREVIEW_CALL = LANDING_COPEN_DOMAIN + "/templates/%s/index-call.html";

    public static final String PT_PATH_TEMPLATE_PREVIEW_NORMAL = LANDING_COPEN_DOMAIN + "/templates/%s/";

    public static class Code {
        public static int SUCCESS = 0;
        public static int ERROR = 1;
    }

    public static class Message {
        public static String SUCCESS = "success";
    }

    public static class Config {
        public static String IP = "IP";
    }
}
