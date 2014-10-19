package org.tomat.translate.brooklyn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.AgnosticElementUtils;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynEntity;
import org.tomat.translate.brooklyn.exceptions.NotSupportedTypeByBrooklynException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jose on 15/10/14.
 */
public class BrooklynTranslatorTest {

    DefinitionParser definitionParser;
    String AWSApplicationDatabaseFile = "resources/AWS-Location-Sample.xml";
    AgnosticApplication agnosticApplication;
    BrooklynTranslator brooklynTranslator;
    BrooklynApplicationEntity brooklynApplicationEntity;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BrooklynTranslatorTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        definitionParser = new DefinitionParser();
        definitionParser
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticApplication=new AgnosticApplication(definitionParser);
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
    }
    //hacer parsing con vacia

    @Test
    public void testBrooklynTranslatorMetadata() {
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
        brooklynApplicationEntity=brooklynTranslator.getBrooklynApplicationEntity();
        assertNotNull(brooklynApplicationEntity);
        assertEquals(brooklynApplicationEntity.getId(), "AppOnlineRetailing");
        assertEquals(brooklynApplicationEntity.getName(), "OnlineRetailing Template");
        assertEquals(brooklynApplicationEntity.getLocation(), BrooklynEntity.LOCATION_BY_DEFAULT);
    }

    @Test
    public void testBrooklynParsing() throws NotSupportedTypeByBrooklynException {
        brooklynTranslator=new BrooklynTranslator(agnosticApplication);
        brooklynTranslator.translate();
    }

}
