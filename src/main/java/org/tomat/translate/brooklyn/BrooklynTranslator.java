package org.tomat.translate.brooklyn;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.translate.TechnologyTranslator;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntityProvider;
import org.tomat.translate.brooklyn.exceptions.NotSupportedTypeByBrooklynException;

import java.util.Set;

/**
 * Created by Jose on 18/10/14.
 */
public class BrooklynTranslator extends TechnologyTranslator {

    private BrooklynApplicationEntity brooklynApplicationEntity;

    public BrooklynTranslator(AgnosticApplication agnosticApplication){
        super(agnosticApplication);
        initBrooklynApplicationEntity();
    }

    private void initBrooklynApplicationEntity() {
        ApplicationAgnosticMetadata metadata=getAgnosticApplication().getAgnosticMetadata();
        brooklynApplicationEntity = (BrooklynApplicationEntity) new BrooklynApplicationEntity
                .Builder()
                .id(metadata.getId())
                .name(metadata.getName())
                .build();
    }

    @Override
    public BrooklynTranslator translate() throws NotSupportedTypeByBrooklynException {
        translateAgnosticElements();
        return this;
    }

    private void translateAgnosticElements() throws NotSupportedTypeByBrooklynException {
        Set<AgnosticElement> agnosticElements = getAgnosticApplication()
                .getAgnosticGraph()
                .getVertexSet();
        BrooklynServiceEntity brooklynServiceEntity;
        for(AgnosticElement agnosticElement: agnosticElements){
            brooklynServiceEntity= (BrooklynServiceEntity) this.getTechnologyComponentTranslation(agnosticElement);
            //TODO hay que configurar relaciones
            addTechnologyComponent(brooklynServiceEntity);
        }
    }

    private void addTechnologyComponent(BrooklynServiceEntity brooklynServiceEntity){
        brooklynApplicationEntity.addService(brooklynServiceEntity);

    }

    @Override
    public BrooklynServiceEntity getTechnologyComponentTranslation(AgnosticElement agnosticElement)
            throws NotSupportedTypeByBrooklynException {
        return BrooklynServiceEntityProvider.createBrooklynServiceEntity(agnosticElement);
    }

    @Override
    public void getTranslation() {

    }

    public BrooklynApplicationEntity getBrooklynApplicationEntity(){
        return brooklynApplicationEntity;
    }




}
