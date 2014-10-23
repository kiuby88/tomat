package org.tomat.translate.brooklyn;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.translate.TechnologyElementsFabric;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntityProvider;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

/**
 * Created by Jose on 23/10/14.
 */
public class BrooklynElementsFabric implements TechnologyElementsFabric{

    @Override
    public BrooklynServiceEntity buildTechnologyComponent(AgnosticElement agnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return BrooklynServiceEntityProvider.createBrooklynServiceEntity(agnosticElement);
    }

}
