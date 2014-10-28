package org.tomat.translate.brooklyn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jose on 15/10/14.
 */
public class BrooklynTranslatorSimpleAppTest {

    DefinitionParser definitionParser;
    String AWSApplicationDatabaseFile = "src/test/resources/toscaTopology/AWS-Location-Sample.xml";
    AgnosticApplication agnosticApplication;
    BrooklynTranslator brooklynTranslator;
    BrooklynApplicationEntity brooklynApplicationEntity;
    String yamlFile="src/test/resources/yaml/test.yaml";

    //TODO refactor test to init using less code
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BrooklynTranslatorSimpleAppTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        definitionParser = new DefinitionParser();
        definitionParser
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticApplication=new AgnosticApplication(definitionParser);
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
    }

    @Test
    public void testBrooklynTranslationCreation_Empty(){
        agnosticApplication=new AgnosticApplication(new DefinitionParser());
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        assertNotNull(brooklynApplicationEntity);
        assertEquals(brooklynApplicationEntity.getId(), ApplicationAgnosticMetadata.APPLICATION_ID_BY_DEFAULT);
        assertEquals(brooklynApplicationEntity.getName(), ApplicationAgnosticMetadata.APPLICATION_NAME_BY_DEFAULT);
        assertEquals(brooklynApplicationEntity.getLocation(), BrooklynEntity.LOCATION_BY_DEFAULT);
        assertEquals(brooklynApplicationEntity.getServices().size(), 0);
    }

    @Test
    public void testBrooklynTranslatorMetadata() {
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        assertNotNull(brooklynApplicationEntity);
        assertEquals(brooklynApplicationEntity.getId(), "AppOnlineRetailing");
        assertEquals(brooklynApplicationEntity.getName(), "OnlineRetailing Template");
        assertEquals(brooklynApplicationEntity.getLocation(), BrooklynEntity.LOCATION_BY_DEFAULT);
    }

    @Test
    public void testBrooklynTranslating_Empty() throws NotSupportedTypeByTechnologyException {
        agnosticApplication=new AgnosticApplication(new DefinitionParser());
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
        brooklynTranslator.translate();
    }

    @Test
    public void testBrooklynTranslating() throws NotSupportedTypeByTechnologyException {
        brooklynTranslator.translate();
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        assertEquals(brooklynApplicationEntity.getServices().size(),1);
        List<BrooklynServiceEntity> service = brooklynApplicationEntity.getServices();
        BrooklynServiceEntity jBossBrooklynService=service.get(0);
        assertNotNull(jBossBrooklynService.getBrooklynConfigProperties());
        assertEquals(jBossBrooklynService.getBrooklynConfigProperties().size(), 1);
        assertEquals(jBossBrooklynService.getBrooklynConfigProperties().get("port.http"), "80+");
    }

    @Test
    public void testBrooklynPrinting()
            throws NotSupportedTypeByTechnologyException, IOException {
        brooklynTranslator.translate();
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        brooklynTranslator.print(yamlFile);
    }


}
