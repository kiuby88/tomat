package org.tomat.agnostic.properties;

import java.util.Map;

/**
 * Created by Kiuby88 on 17/10/14.
 */
public class HttpAgnosticProperty extends AgnosticProperty implements PortAgnosticProperty{

    private final static String[] HTTP_PROPERTY = {"http", "httpport"};

    public HttpAgnosticProperty( Map<String, String > properties){
        super(properties);
    }

    @Override
    public String[] getAllowedPropertyIds(){
        return HTTP_PROPERTY;
    }
}
