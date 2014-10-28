package org.tomat.agnostic.application.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentUtils;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Jose on 15/10/14.
 */
public class AppDatabaseAgnosticApplicationTest {

    ToscaProcessor toscaProcessor;
    String AWSApplicationDatabaseFile = "src/test/resources/toscaTopology/AWS-Application-DatabaseSample-JBoss.xml";
    AgnosticApplication agnosticApplication;
    private AgnosticGraph agnosticGraph;

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatabaseAgnosticApplicationTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        toscaProcessor = new ToscaProcessor();
        toscaProcessor
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticApplication=new AgnosticApplication(toscaProcessor);
        agnosticGraph=agnosticApplication.getAgnosticGraph();
    }

    @Test
    public void testCreation_definitionEmpty() {
        agnosticApplication=new AgnosticApplication(new ToscaProcessor());
        assertNotNull(agnosticApplication);
        assertNotNull(agnosticApplication.getAgnosticGraph());
    }

    @Test
    public void testMetadata_definitionEmpty() {
        agnosticApplication=new AgnosticApplication(new ToscaProcessor());
        ApplicationAgnosticMetadata metadata = agnosticApplication.getAgnosticMetadata();
        assertEquals(metadata.getId(), ApplicationAgnosticMetadata.APPLICATION_ID_BY_DEFAULT);
        assertEquals(metadata.getName(), ApplicationAgnosticMetadata.APPLICATION_NAME_BY_DEFAULT);
    }

    @Test
    public void testMetadata(){
        ApplicationAgnosticMetadata metadata=
                agnosticApplication.getAgnosticMetadata();

        assertEquals(metadata.getId(), "dbApp");
        assertEquals(metadata.getName(), "DatabaseApp");
    }

    @Test
    public void testNumberOfVertex() {
        assertEquals(agnosticApplication.getAgnosticGraph().getVertexSet().size(), 4);
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
