package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.translate.TechnologyVisitorRelationConfiguration;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfiguration;

import java.util.Map;

/**
 * Created by Kiuby88 on 19/10/14.
 */
//TODO could be a good idea move this class (and other Services to a new package named services
// TODO into .entity
public class TomcatBrooklynService extends JavaWebApplicationServerBrooklynService {

    private final static String SERVICE_TYPE = "org.apache.brooklyn.entity.webapp.tomcat.TomcatServer";


    public TomcatBrooklynService(AgnosticComponent agnosticComponent) {
        super(agnosticComponent);
        setServiceType(SERVICE_TYPE);
    }

    @Override
    public Map<Class<? extends AgnosticProperty>, String> getSupportedAgnosticAndBrooklynPropertyId() {
        Map<Class<? extends AgnosticProperty>, String> result =
                super.getSupportedAgnosticAndBrooklynPropertyId();
        //Here we could add new
        return result;
    }

    @Override
    public void accept(TechnologyVisitorRelationConfiguration visit,
                       AgnosticComponent agnosticComponent,
                       AgnosticGraph agnosticGraph) {
        ((BrooklynVisitorRelationConfiguration) visit)
                .visit(this, agnosticComponent, agnosticGraph);
    }
}
