package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TNodeTemplate;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.HttpAgnosticProperty;
import org.tomat.agnostic.properties.HttpsAgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;

import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class WebAppAgnosticElement extends AgnosticElement {

    public final static String TYPE="WebApplication";

    public WebAppAgnosticElement(TNodeTemplate nodeTemplateSource)
            throws AgnosticPropertyException {
        super(nodeTemplateSource);
    }

    @Override
    public  String getType() {
        return TYPE;
    }





}
