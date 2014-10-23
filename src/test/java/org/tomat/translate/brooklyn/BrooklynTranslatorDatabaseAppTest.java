package org.tomat.translate.brooklyn;

/**
 * Created by Jose on 20/10/14.
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by Jose on 15/10/14.
 */
public class BrooklynTranslatorDatabaseAppTest {

    DefinitionParser definitionParser;
    String AWSApplicationDatabaseFile = "resources/AWS-Application-DatabaseSample.xml";
    AgnosticApplication agnosticApplication;
    BrooklynTranslator brooklynTranslator;
    BrooklynApplicationEntity brooklynApplicationEntity;
    List<BrooklynServiceEntity> services;

    //TODO refactor test to init using less code
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BrooklynTranslatorDatabaseAppTest.class);
    }

    @Before
    public void setUp()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException,
            AgnosticPropertyException, NotSupportedTypeByTechnologyException {

        definitionParser=new DefinitionParser();
        definitionParser
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticApplication = new AgnosticApplication(definitionParser);
        brooklynTranslator = new BrooklynTranslator(agnosticApplication)
                .translate();
        brooklynApplicationEntity = brooklynTranslator.getBrooklynApplicationEntity();
        services = brooklynApplicationEntity.getServices();
    }

    @Test
    public void testBrooklynServiceEntitiesSize() {
        assertEquals(brooklynApplicationEntity.getServices().size(), 2);
    }

    @Test
    public void testBrooklynTranslatorDbAppMetadata() {
        assertNotNull(brooklynApplicationEntity);
        assertEquals(brooklynApplicationEntity.getId(), "dbApp");
        assertEquals(brooklynApplicationEntity.getName(), "DatabaseApp");
        assertEquals(brooklynApplicationEntity.getLocation(), BrooklynEntity.LOCATION_BY_DEFAULT);
    }

    @Test
    public void testBrooklynTranslating_JBoss()
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {

        BrooklynServiceEntity jBossBrooklynService = services.get(0);
        assertNotNull(jBossBrooklynService.getBrooklynConfigProperties());
        assertEquals(jBossBrooklynService.getBrooklynConfigProperties().size(), 2);
        assertEquals(jBossBrooklynService.getBrooklynConfigProperties().get("port.http"), "80+");
    }

    @Test
    public void testBrooklynTranslating_MySQL()
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {

        BrooklynServiceEntity mySQL = services.get(1);
        assertNotNull(mySQL);
        assertNotNull(mySQL.getBrooklynConfigProperties());
        assertEquals(mySQL.getBrooklynConfigProperties().size(), 1);
    }

    @Test
    public void testBrooklynPrinting()
            throws AgnosticComponentTypeNotSupportedbyBrooklyException, IOException {

        brooklynApplicationEntity = brooklynTranslator.getBrooklynApplicationEntity();
        brooklynTranslator.print("resources/testDbApp.yaml");
    }
}
