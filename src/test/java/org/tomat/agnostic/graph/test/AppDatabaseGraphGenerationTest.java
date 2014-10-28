package org.tomat.agnostic.graph.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentUtils;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jose on 15/10/14.
 */
public class AppDatabaseGraphGenerationTest {

    ToscaProcessor toscaProcessor;
    String AWSApplicationDatabaseFile = "src/test/resources/toscaTopology/AWS-Application-DatabaseSample-JBoss.xml";
    AgnosticGraph agnosticGraph;
    Set<AgnosticComponent> agnosticComponentVertexs;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatabaseGraphGenerationTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        toscaProcessor = new ToscaProcessor();
        toscaProcessor
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticGraph = new AgnosticGraph(
                toscaProcessor.getAgnosticComponents(),
                toscaProcessor.getAgnosticRelations());
        agnosticComponentVertexs = agnosticGraph.getVertexSet();
    }

    @Test
    public void testGraphCreation_DefinitionEmpty(){
        toscaProcessor =new ToscaProcessor();
        agnosticGraph=new AgnosticGraph(toscaProcessor.getAgnosticComponents(),
                toscaProcessor.getAgnosticRelations());
        assertNotNull(agnosticGraph);
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
        List<AgnosticComponent> independentAgnosticComponents = agnosticGraph.getIndependentVertex();
        assertEquals(independentAgnosticComponents.size(), 2);
        assertNotNull(AgnosticComponentUtils
                .findAgnosticComponentById(independentAgnosticComponents, "JbossMainWebServer"));
        assertNotNull(AgnosticComponentUtils
                .findAgnosticComponentById(independentAgnosticComponents, "MainMySql"));
    }
}
