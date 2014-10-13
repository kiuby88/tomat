package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.AgnosticApplicationComponent;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;

import java.util.List;
import java.util.Map;

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
        Map<String, List<String>> agnosticRelations = definitionParser.getAgnosticApplicationsComponentRelations();
        assertEquals(agnosticRelations.size(),1);
        assertEquals(agnosticRelations.containsKey(mainWebAppId),true);
        assertEquals(agnosticRelations.get(mainWebAppId).size(),1);
        assertEquals(agnosticRelations.get(mainWebAppId).get(0), jBossMainWebServerId);
    }

    @Test
    public void agnosticComponentsGenerationCorrect()
            throws TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        definitionParser
                .parsingApplicationTopology(AWSFile)
                .buildAgnosticsElements();
        List<AgnosticApplicationComponent> agnosticComponents = definitionParser.getAgnosticApplicationComponents();
        assertEquals(agnosticComponents.size(), 0);
    }
}
