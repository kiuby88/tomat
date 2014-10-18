package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public class HttpAgnosticProperty extends AgnosticProperty{

    private final static String[] HTTP_PROPERTY = {"http", "httpport"};

    public HttpAgnosticProperty(String id, Map<String, String > properties){
        super(id, properties);
    }

    @Override
    public String[] getAllowedPropertyIds(){
        return HTTP_PROPERTY;
    }
}
