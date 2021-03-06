package org.tomat.agnostic.components;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.HttpAgnosticProperty;
import org.tomat.agnostic.properties.HttpsAgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;

import java.util.Map;

/**
 * Created by kiuby88 on 24/09/2014.
 */
public abstract class JavaWebApplicationServerAgnosticComponent extends AgnosticComponent {

    public final static String TYPE="JavaWebApplicationServer";

    public JavaWebApplicationServerAgnosticComponent(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);

    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties(){
        //TODO delete the super of super.getExpectedProperties and check the test
        //TODO delete id and using a list of Class
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        map.put("http", HttpAgnosticProperty.class);
        map.put("https", HttpsAgnosticProperty.class);
        return map;
    }

    @Override
    public  String getType() {
        return TYPE;
    }
}
