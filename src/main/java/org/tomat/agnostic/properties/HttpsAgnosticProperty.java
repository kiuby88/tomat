package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class HttpsAgnosticProperty extends AgnosticProperty implements PortAgnosticProperty{

    private final static String[] HTTPS_PROPERTY = {"https", "httpsport"};

    public HttpsAgnosticProperty( Map<String, String > properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds() {
        return HTTPS_PROPERTY;
    }
}
