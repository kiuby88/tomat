package org.tomat.agnostic.components;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyComponentFactory;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class TomcatAgnosticComponent extends JavaWebApplicationServerAgnosticComponent {

    public final static String TYPE="TomcatWebServer";

    public TomcatAgnosticComponent(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties(){
        //TODO delete the super of super.getExpectedProperties and check the test
        //TODO delete id and using a list of Class
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        //map.put("http", HttpAgnosticProperty.class);
        //map.put("https", HttpsAgnosticProperty.class);
        return map;
    }

    @Override
    public TechnologyComponent buildTechnologyComponent(TechnologyComponentFactory factory)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return factory.buildTechnologyComponent(this);
    }

    @Override
    public  String getType() {
        return TYPE;
    }
}
