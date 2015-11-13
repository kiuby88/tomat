package org.tomat.translate.brooklyn;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.ResourcePathResolver;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.exceptions.BrooklynVisitorRelationConfigurationNotSupportedType;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kiuby88 on 15/10/14.
 */
public class BrooklynTranslatorSimpleAppTest {

    ToscaProcessor toscaProcessor;
    String file;
    AgnosticApplication agnosticApplication;
    BrooklynTranslator brooklynTranslator;
    BrooklynApplicationEntity brooklynApplicationEntity;
    String yamlFile;

    public BrooklynTranslatorSimpleAppTest()
            throws AgnosticPropertyException, TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException {
        setUp();
    }

    //TODO refactor test to init using less code
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BrooklynTranslatorSimpleAppTest.class);
    }

    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        ResourcePathResolver resourcePathResolver= new ResourcePathResolver();
        file=resourcePathResolver.getPathFile(ResourcePathResolver.AWS_LOCATION_SAMPLE);
        yamlFile=resourcePathResolver.getPathFile(ResourcePathResolver.SIMPLE_DB_APP_YAML);
        toscaProcessor = new ToscaProcessor();
        toscaProcessor
                .parsingApplicationTopology(file).buildAgnostics();
        agnosticApplication=new AgnosticApplication(toscaProcessor);
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
    }

    @Test
    public void testBrooklynTranslationCreation_Empty(){
        agnosticApplication=new AgnosticApplication(new ToscaProcessor());
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        Assert.assertNotNull(brooklynApplicationEntity);
        assertEquals(brooklynApplicationEntity.getId(), ApplicationAgnosticMetadata.APPLICATION_ID_BY_DEFAULT);
        assertEquals(brooklynApplicationEntity.getName(), ApplicationAgnosticMetadata.APPLICATION_NAME_BY_DEFAULT);
        assertEquals(brooklynApplicationEntity.getLocation(), BrooklynEntity.LOCATION_BY_DEFAULT);
        assertEquals(brooklynApplicationEntity.getServices().size(), 0);
    }

    @Test
    public void testBrooklynTranslatorMetadata() {
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        Assert.assertNotNull(brooklynApplicationEntity);
        assertEquals(brooklynApplicationEntity.getId(), "AppOnlineRetailing");
        assertEquals(brooklynApplicationEntity.getName(), "OnlineRetailing Template");
        assertEquals(brooklynApplicationEntity.getLocation(), BrooklynEntity.LOCATION_BY_DEFAULT);
    }

    @Test
    public void testBrooklynTranslating_Empty()
            throws NotSupportedTypeByTechnologyException,
            BrooklynVisitorRelationConfigurationNotSupportedType {
        agnosticApplication=new AgnosticApplication(new ToscaProcessor());
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
        brooklynTranslator.translate();
    }

    @Test
    public void testBrooklynTranslating() throws NotSupportedTypeByTechnologyException,
            BrooklynVisitorRelationConfigurationNotSupportedType {
        brooklynTranslator.translate();
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        assertEquals(brooklynApplicationEntity.getServices().size(),1);
        List<BrooklynServiceEntity> service = brooklynApplicationEntity.getServices();
        BrooklynServiceEntity jBossBrooklynService=service.get(0);
        Assert.assertNotNull(jBossBrooklynService.getBrooklynConfigProperties());
        assertEquals(jBossBrooklynService.getBrooklynConfigProperties().size(), 1);
        assertEquals(jBossBrooklynService.getBrooklynConfigProperties().get("port.http"), "80+");
    }

    @Test
    public void testBrooklynPrinting()
            throws NotSupportedTypeByTechnologyException, IOException,
            BrooklynVisitorRelationConfigurationNotSupportedType {
        brooklynTranslator.translate();
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        brooklynTranslator.print(yamlFile);
    }


}
