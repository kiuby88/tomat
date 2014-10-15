package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.elements.AgnosticElementUtils;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.tosca.parsers.DefinitionParser;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Jose on 04/10/14.
 */

public class DefinitionParserTest {

    DefinitionParser definitionParser;
    String AWSFileMalFormedRelation = "resources/AWS-Location-Sample-MalFormedRelation.xml";
    String AWSFile = "resources/AWS-Location-Sample.xml";
    String jBossMainWebServerId ="JBossMainWebServer".toLowerCase();
    String mainWebAppId="MainWebApp".toLowerCase();

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DefinitionParserTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException {

        definitionParser=new DefinitionParser();
    }

    @Test(expected = TopologyTemplateFormatException.class)
    public void definitionThrowExceptionByMalformedRelation()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException{
        definitionParser
                .parsingApplicationTopology(AWSFileMalFormedRelation)
                .buildAgnosticsElements();
    }

    @Test
    public void agnosticRelationComponentsGenerationCorrect()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        definitionParser
                .parsingApplicationTopology(AWSFile)
                .buildAgnosticsElements();
        Map<AgnosticElement, List<AgnosticElement>> agnosticRelations = definitionParser.getAgnosticApplicationsComponentRelations();
        assertEquals(agnosticRelations.size(),1);
        Set<AgnosticElement> keySet=agnosticRelations.keySet();

        AgnosticElement agnosticElementMainWebApp=AgnosticElementUtils.findAgnosticElementById(keySet,mainWebAppId);
        assertNotNull(agnosticElementMainWebApp);
        assertEquals(agnosticRelations.containsKey(agnosticElementMainWebApp),true);
        assertEquals(agnosticRelations.get(agnosticElementMainWebApp).size(),1);
        assertEquals(agnosticRelations.get(agnosticElementMainWebApp).get(0).getId().toLowerCase(), jBossMainWebServerId);
    }

    @Test
    public void agnosticComponentsGenerationCorrect()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        definitionParser
                .parsingApplicationTopology(AWSFile)
                .buildAgnosticsElements();
        List<AgnosticElement> agnosticComponents = definitionParser.getAgnosticApplicationComponents();
        assertEquals(agnosticComponents.size(), 2);
    }
}