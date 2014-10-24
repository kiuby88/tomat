package org.tomat.translate.brooklyn;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyElementsFactory;
import org.tomat.translate.TechnologyTranslator;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.print.ReversedPropertyUtils;
import org.tomat.translate.brooklyn.print.SkipEmptyAndNullRepresenter;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfiguration;
import org.tomat.translate.brooklyn.visit.BrooklynVisitorRelationConfigurationProvider;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by Jose on 18/10/14.
 */
public class BrooklynTranslator extends TechnologyTranslator {

    private BrooklynApplicationEntity brooklynApplicationEntity;
    private BrooklynElementsFactory brooklynElementsFactory;

    public BrooklynTranslator(AgnosticApplication agnosticApplication){
        super(agnosticApplication);
        initBrooklynApplicationEntity();
        brooklynElementsFactory =new BrooklynElementsFactory();
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
        return brooklynElementsFactory;
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
        List<AgnosticElement> incomingVertexList=agnosticGraph.getIncompongVertexOf(agnosticElementOfBrooklynService);
        BrooklynVisitorRelationConfiguration visitor;

        for(AgnosticElement incomingVertex : incomingVertexList){
            visitor=BrooklynVisitorRelationConfigurationProvider.createVisit(incomingVertex);
            technologyServiceEntity.accept(visitor, incomingVertex, agnosticGraph);
        }
    }

    private void addTechnologyComponent(BrooklynServiceEntity brooklynServiceEntity){
        brooklynApplicationEntity.addService(brooklynServiceEntity);
    }

    @Override
    public void getTranslation() {

    }

    public BrooklynApplicationEntity getBrooklynApplicationEntity(){
        return brooklynApplicationEntity;
    }

    public void print(String file) throws IOException {
        print(new FileWriter(file));
    }

    //TODO refactor
    public void print(FileWriter file){
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        //options
        //options.setCanonical(false);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        SkipEmptyAndNullRepresenter skipEmptyAndNullRepresenter=new SkipEmptyAndNullRepresenter();

        skipEmptyAndNullRepresenter.addClassTag(org.tomat.translate.brooklyn.entity.JBossBrooklynService.class, Tag.MAP);
        skipEmptyAndNullRepresenter.addClassTag(org.tomat.translate.brooklyn.entity.MySQLBrooklynService.class, Tag.MAP);
        skipEmptyAndNullRepresenter.addClassTag(BrooklynApplicationEntity.class, Tag.MAP);

        skipEmptyAndNullRepresenter.setPropertyUtils(new ReversedPropertyUtils());

        Yaml yaml=new Yaml(skipEmptyAndNullRepresenter, options);
        yaml.dump(this.getBrooklynApplicationEntity(), file);
    }



}
