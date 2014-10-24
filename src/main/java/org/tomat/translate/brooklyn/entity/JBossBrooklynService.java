package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.agnostic.properties.HttpAgnosticProperty;
import org.tomat.agnostic.properties.HttpsAgnosticProperty;
import org.tomat.translate.TechnologyVisitorRelationConfiguration;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfiguration;

import java.util.Map;

/**
 * Created by Jose on 19/10/14.
 */
//TODO could be a good idea move this class (and other Services to a new package named services
// TODO into .entity
public class JBossBrooklynService extends BrooklynServiceEntity {

    private final static String SERVICE_TYPE="brooklyn.entity.webapp.jboss.JBoss7Server";


    public JBossBrooklynService(AgnosticElement agnosticElement) {
        super(agnosticElement);
        setServiceType(SERVICE_TYPE);
    }

    @Override
    public Map<Class<? extends AgnosticProperty>, String > getSupportedAgnosticPropertiesAndBrooklynPropertyId(){
        Map<Class<? extends AgnosticProperty>, String > result=
                super.getSupportedAgnosticPropertiesAndBrooklynPropertyId();
        result.put(HttpAgnosticProperty.class, "port.http");
        result.put(HttpsAgnosticProperty.class, "port.https");
        return result;
    }

    @Override
    public void accept(TechnologyVisitorRelationConfiguration visit,
                       AgnosticElement agnosticElement,
                       AgnosticGraph agnosticGraph) {
        ((BrooklynVisitorRelationConfiguration)visit)
                .visit(this, agnosticElement, agnosticGraph);
    }
}