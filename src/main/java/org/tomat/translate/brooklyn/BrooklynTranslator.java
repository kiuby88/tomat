package org.tomat.translate.brooklyn;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.translate.TechnologyTranslator;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntityProvider;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
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
    public BrooklynTranslator translate() throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        translateAgnosticElements();
        return this;
    }

    private void translateAgnosticElements() throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        Set<AgnosticElement> agnosticElements = getAgnosticApplication()
                .getAgnosticGraph()
                .getVertexSet();
        BrooklynServiceEntity brooklynServiceEntity;
        for(AgnosticElement agnosticElement: agnosticElements){
            brooklynServiceEntity= this.getTechnologyComponentTranslation(agnosticElement);
            //TODO refactoring
            if(brooklynServiceEntity!=null){
                //TODO hay que configurar relaciones antes de añadirlo
                addTechnologyComponent(brooklynServiceEntity);
            }
        }
    }

    private void addTechnologyComponent(BrooklynServiceEntity brooklynServiceEntity){
        brooklynApplicationEntity.addService(brooklynServiceEntity);
    }

    @Override
    public BrooklynServiceEntity getTechnologyComponentTranslation(AgnosticElement agnosticElement)
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        return BrooklynServiceEntityProvider.createBrooklynServiceEntity(agnosticElement);
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
        options.setCanonical(false);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        Yaml yaml=new Yaml(options);
        yaml.dump(this.getBrooklynApplicationEntity(), file);
    }




}
