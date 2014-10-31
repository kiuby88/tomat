package org.tomat.agnostic.components;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.*;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyComponentFactory;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

import java.util.Map;

/**
 * Created by Kiuby88 on 24/09/2014.
 */
public class MySQLDataBaseAgnosticComponent extends AgnosticComponent {

    public final static String TYPE = "MySQL";

    public MySQLDataBaseAgnosticComponent(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties() {
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        map.put("dbName", MySQLDbNameAgnosticProperty.class);
        map.put("dbUser", MySQLDbUserAgnosticProperty.class);
        map.put("dbPassword", MySQLDbPasswordAgnosticProperty.class);
        map.put("dbPort", MySQLDbPortAgnosticPropertyAgnosticProperty.class);
        return map;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public TechnologyComponent buildTechnologyComponent(TechnologyComponentFactory factory)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return factory.buildTechnologyComponent(this);
    }
}
