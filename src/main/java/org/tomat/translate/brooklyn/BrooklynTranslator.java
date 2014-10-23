package org.tomat.translate.brooklyn;

import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.JBossAgnosticElement;
import org.tomat.translate.TechnologyTranslator;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntityProvider;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Representer;

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
        //options
        //options.setCanonical(false);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        SkipEmptyAndNullRepresenter skipEmptyAndNullRepresenter=new SkipEmptyAndNullRepresenter();

        skipEmptyAndNullRepresenter.addClassTag(org.tomat.translate.brooklyn.entity.JBossBrooklynService.class, Tag.MAP);
        skipEmptyAndNullRepresenter.addClassTag(org.tomat.translate.brooklyn.entity.MySQLBrooklynService.class, Tag.MAP);
        skipEmptyAndNullRepresenter.addClassTag(BrooklynApplicationEntity.class, Tag.MAP);

        Yaml yaml=new Yaml(skipEmptyAndNullRepresenter, options);
        yaml.dump(this.getBrooklynApplicationEntity(), file);
    }

    private class SkipEmptyAndNullRepresenter extends Representer {

        @Override
        protected NodeTuple representJavaBeanProperty(Object javaBean, Property property,
                                                      Object propertyValue, Tag customTag) {
            NodeTuple tuple = super.representJavaBeanProperty(javaBean, property, propertyValue,
                    customTag);
            NodeTuple result = tuple;
            if (valueIsNull(tuple)
                    || isAEmptyCollection(tuple)) {
                result=null;
            }
            return result;
        }

        public boolean valueIsNull(NodeTuple tuple) {
            Node valueNode = tuple.getValueNode();
            return Tag.NULL.equals(valueNode.getTag());
        }

        public boolean isAEmptyCollection(NodeTuple tuple) {
            return isASeqEmptyCollection(tuple)
                    || isAMapEmptyCollection(tuple);
        }

        public boolean isASeqEmptyCollection(NodeTuple tuple) {
            boolean result = false;
            Node valueNode = tuple.getValueNode();
            if (isASeqCollection(tuple)) {
                SequenceNode seq = (SequenceNode) valueNode;
                result=seq.getValue().isEmpty();
            }
            return result;
        }

        public boolean isASeqCollection(NodeTuple tuple) {
            Node valueNode = tuple.getValueNode();
            return isACollection(tuple)
                    && Tag.SEQ.equals(valueNode.getTag());
        }

        public boolean isACollection(NodeTuple tuple) {
            Node valueNode = tuple.getValueNode();
            return valueNode instanceof CollectionNode;
        }

        public boolean isAMapEmptyCollection(NodeTuple tuple) {
            boolean result = false;
            Node valueNode = tuple.getValueNode();
            if (isAMapCollection(tuple)) {
                MappingNode seq = (MappingNode) valueNode;
                result=seq.getValue().isEmpty();
            }
            return result;
        }

        public boolean isAMapCollection(NodeTuple tuple) {
            Node valueNode = tuple.getValueNode();
            return isACollection(tuple)
                    && Tag.MAP.equals(valueNode.getTag());
        }
    }


}
