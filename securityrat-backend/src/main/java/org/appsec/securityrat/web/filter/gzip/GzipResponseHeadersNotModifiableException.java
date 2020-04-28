package org.appsec.securityrat.web.filter.gzip;

import javax.servlet.ServletException;

public class GzipResponseHeadersNotModifiableException extends ServletException {
    public GzipResponseHeadersNotModifiableException(String message) {
        super(message);
    }
}
