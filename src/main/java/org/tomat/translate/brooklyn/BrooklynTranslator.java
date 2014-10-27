package org.tomat.translate.brooklyn;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyElementsFactory;
import org.tomat.translate.TechnologyTranslator;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynComponentFactory;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.print.BrooklynYamlPrinter;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfiguration;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfigurationProvider;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Jose on 18/10/14.
 */
public class BrooklynTranslator extends TechnologyTranslator {

    private BrooklynApplicationEntity brooklynApplicationEntity;
    private BrooklynComponentFactory brooklynComponentFactory;

    public BrooklynTranslator(AgnosticApplication agnosticApplication){
        super(agnosticApplication);
        initBrooklynApplicationEntity();
        brooklynComponentFactory =new BrooklynComponentFactory();
    }

    private void initBrooklynApplicationEntity() {
        ApplicationAgnosticMetadata metadata=getAgnosticApplication().getAgnosticMetadata();
        brooklynApplicationEntity = (BrooklynApplicationEntity) new BrooklynApplicationEntity
                .Builder()
                .id(metadata.getId())
                .name(metadata.getName())
                .build();
    }

    public TechnologyElementsFactory getTechnologyElementFactory(){
        return brooklynComponentFactory;
    }


    @Override
    public BrooklynTranslator translate()
            throws NotSupportedTypeByTechnologyException {
        translateAgnosticElements();
        return this;
    }

    private void translateAgnosticElements()
            throws NotSupportedTypeByTechnologyException {
        Set<AgnosticElement> agnosticElements = getAgnosticApplication()
                .getAgnosticGraph()
                .getVertexSet();
        BrooklynServiceEntity brooklynServiceEntity;
        for(AgnosticElement agnosticElement: agnosticElements){

            brooklynServiceEntity= buildBrooklynComponent(agnosticElement);

            if(brooklynServiceEntity!=null){
                configureRelations(brooklynServiceEntity);
                addTechnologyComponent(brooklynServiceEntity);
            }
        }
    }

    private BrooklynServiceEntity buildBrooklynComponent(AgnosticElement agnosticElement)
            throws NotSupportedTypeByTechnologyException {

        TechnologyElementsFactory factory = getTechnologyElementFactory();
        return (BrooklynServiceEntity) agnosticElement.buildTechnologyComponent(factory);
    }

    @Override
    public void configureRelations(TechnologyComponent technologyServiceEntity){

        AgnosticGraph agnosticGraph=getAgnosticApplication().getAgnosticGraph();
        AgnosticElement agnosticElementOfBrooklynService=technologyServiceEntity.getAgnosticElement();
        List<AgnosticElement> incomingVertexList=agnosticGraph.getIncomingVertexOf(agnosticElementOfBrooklynService);
        BrooklynVisitorRelationConfiguration visitor;

        for(AgnosticElement incomingVertex : incomingVertexList){
            visitor=BrooklynVisitorRelationConfigurationProvider.createVisit(incomingVertex);
            technologyServiceEntity.accept(visitor, incomingVertex, agnosticGraph);
        }
    }

    private void addTechnologyComponent(BrooklynServiceEntity brooklynServiceEntity){
        brooklynApplicationEntity.addService(brooklynServiceEntity);
    }


    public BrooklynApplicationEntity getBrooklynApplicationEntity(){
        return brooklynApplicationEntity;
    }

    @Override
    public String  print() throws IOException {
        BrooklynYamlPrinter printer = new BrooklynYamlPrinter();
        return printer.print(this.getBrooklynApplicationEntity());
    }

    @Override
    public void print(String file) throws IOException {
        BrooklynYamlPrinter printer = new BrooklynYamlPrinter();
        printer.print(this.getBrooklynApplicationEntity(), file);
    }



}
