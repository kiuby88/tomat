package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.*;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyElementsFactory;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class MySQLDataBaseAgnosticElement extends AgnosticElement {

    public final static String TYPE = "MySQL";

    public MySQLDataBaseAgnosticElement(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties() {
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        map.put("dbName", MySQLDbNameAgnosticPropertyAgnosticProperty.class);
        map.put("dbUser", MySQLDbUserAgnosticPropertyAgnosticProperty.class);
        map.put("dbPassword", MySQLDbPasswordAgnosticPropertyAgnosticProperty.class);
        map.put("dbPort", MySQLDbPortAgnosticPropertyAgnosticProperty.class);
        return map;
    }

    @Override
    public String getType() {
        return TYPE;
    }


    @Override
    public TechnologyComponent buildTechnologyComponent(TechnologyElementsFactory factory)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return factory.buildTechnologyComponent(this);
    }

}
