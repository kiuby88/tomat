package org.tomat.agnostic.graph.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.elements.AgnosticElementUtils;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jose on 15/10/14.
 */
public class AppDatabaseGraphGenerationTest {

    DefinitionParser definitionParser;
    String AWSApplicationDatabaseFile = "resources/AWS-Application-DatabaseSample.xml";
    AgnosticGraph agnosticGraph;
    Set<AgnosticElement> agnosticElementVertex;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatabaseGraphGenerationTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        definitionParser = new DefinitionParser();
        definitionParser
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticGraph = new AgnosticGraph(definitionParser.getAgnosticElements(),
                definitionParser.getAgnosticRelations());
        agnosticElementVertex = agnosticGraph.getVertexSet();
    }

    @Test
    public void testNumberOfEdges() {
        assertEquals(agnosticGraph.getNumberOfEdges(), 3);
    }

    @Test
    public void testNumberOfVertex() {
        assertEquals(agnosticGraph.getVertexSet().size(), 4);
    }

    @Test
    public void testRelations_OutgoingEdges(){
        List<AgnosticElement> independentAgnosticElements = agnosticGraph.getIndependentVertex();

        assertEquals(independentAgnosticElements.size(), 2);
        assertNotNull(AgnosticElementUtils
                .findAgnosticElementById(independentAgnosticElements, "JbossMainWebServer"));
        assertNotNull(AgnosticElementUtils
                .findAgnosticElementById(independentAgnosticElements, "MainMySql"));
    }
}
