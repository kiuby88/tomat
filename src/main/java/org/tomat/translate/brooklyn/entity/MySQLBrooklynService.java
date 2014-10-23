package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.TechnologyVisitorRelationConfiguration;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfiguration;

/**
 * Created by Jose on 19/10/14.
 */
//TODO could be a good idea move this class (and other Services to a new package named services
// TODO into .entity
public class MySQLBrooklynService extends BrooklynServiceEntity {

    private final static String SERVICE_TYPE="brooklyn.entity.database.mysql.MySqlNode";

    public MySQLBrooklynService(AgnosticElement agnosticElement) {
        super(agnosticElement);
        setServiceType(SERVICE_TYPE);
    }

    @Override
    public void accept(TechnologyVisitorRelationConfiguration visit,
                       AgnosticElement agnosticElement,
                       AgnosticGraph agnosticGraph) {
        ((BrooklynVisitorRelationConfiguration)visit)
                .visit(this, agnosticElement, agnosticGraph);
    }

}
