package org.tomat.translate.brooklyn;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyComponentFactory;
import org.tomat.translate.TechnologyTranslator;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynComponentFactory;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.exceptions.BrooklynVisitorRelationConfigurationNotSupportedType;
import org.tomat.translate.brooklyn.print.BrooklynYamlPrinter;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfiguration;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfigurationProvider;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Kiuby88 on 18/10/14.
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

    public TechnologyComponentFactory getTechnologyComponentFactory(){
        return brooklynComponentFactory;
    }


    @Override
    public BrooklynTranslator translate()
            throws NotSupportedTypeByTechnologyException,
            BrooklynVisitorRelationConfigurationNotSupportedType {
        translateAgnosticComponents();
        return this;
    }

    private void translateAgnosticComponents()
            throws NotSupportedTypeByTechnologyException,
            BrooklynVisitorRelationConfigurationNotSupportedType {
        Set<AgnosticComponent> agnosticComponents = getAgnosticApplication()
                .getAgnosticGraph()
                .getVertexSet();
        BrooklynServiceEntity brooklynServiceEntity;
        for(AgnosticComponent agnosticComponent: agnosticComponents){

            brooklynServiceEntity= buildBrooklynComponent(agnosticComponent);

            if(brooklynServiceEntity!=null){
                configureRelations(brooklynServiceEntity);
                addTechnologyComponent(brooklynServiceEntity);
            }
        }
    }

    private BrooklynServiceEntity buildBrooklynComponent(AgnosticComponent agnosticComponent)
            throws NotSupportedTypeByTechnologyException {

        TechnologyComponentFactory factory = getTechnologyComponentFactory();
        return (BrooklynServiceEntity) agnosticComponent.buildTechnologyComponent(factory);
    }

    @Override
    public void configureRelations(TechnologyComponent technologyServiceEntity)
            throws BrooklynVisitorRelationConfigurationNotSupportedType {

        AgnosticGraph agnosticGraph=getAgnosticApplication().getAgnosticGraph();
        AgnosticComponent agnosticComponentOfBrooklynService=technologyServiceEntity.getAgnosticComponent();
        List<AgnosticComponent> incomingVertexList=agnosticGraph.getIncomingVertexOf(agnosticComponentOfBrooklynService);
        BrooklynVisitorRelationConfiguration visitor;

        for(AgnosticComponent incomingVertex : incomingVertexList){
            visitor= BrooklynVisitorRelationConfigurationProvider.createVisit(incomingVertex);
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
