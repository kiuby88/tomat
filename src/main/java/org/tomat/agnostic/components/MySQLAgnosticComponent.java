package org.tomat.agnostic.components;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.MySQLRootPasswordAgnosticPropertyAgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyComponentFactory;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class MySQLAgnosticComponent extends AgnosticComponent {

    public static final String  TYPE="MySQLDB";

    public MySQLAgnosticComponent(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
    }

    @Override
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties() {
        Map<String, Class<? extends AgnosticProperty>> map = super.getExpectedProperties();
        map.put("rootPassword", MySQLRootPasswordAgnosticPropertyAgnosticProperty.class);
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
