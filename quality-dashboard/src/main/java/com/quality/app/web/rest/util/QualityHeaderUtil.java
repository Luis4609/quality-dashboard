package com.quality.app.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class QualityHeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(QualityHeaderUtil.class);

    public QualityHeaderUtil() {
    }

    public static HttpHeaders createAlert(String applicationName, String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-alert", message);

        headers.add("X-" + applicationName + "-params", URLEncoder.encode(param, StandardCharsets.UTF_8));

        return headers;
    }

    public static HttpHeaders createEntityUploadAlert(String applicationName, boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation ? applicationName + "." + entityName + ".fileUploaded" : "A " + entityName + " is uploaded with identifier " + param;
        return createAlert(applicationName, message, param);
    }
}
