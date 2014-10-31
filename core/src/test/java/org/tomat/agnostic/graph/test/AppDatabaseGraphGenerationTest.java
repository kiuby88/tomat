package org.tomat.agnostic.graph.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.tomat.ResourcePathResolver;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentUtils;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kiuby88 on 15/10/14.
 */

@RunWith(value = Parameterized.class)
public class AppDatabaseGraphGenerationTest {

    ToscaProcessor toscaProcessor;
    String webServerId;
    String AWSApplicationDatabaseFile;
    AgnosticGraph agnosticGraph;
    Set<AgnosticComponent> agnosticComponentVertexs;

    public AppDatabaseGraphGenerationTest(String name, String  file, String webServerId)
            throws AgnosticPropertyException, TopologyTemplateFormatException, NodeTemplateTypeNotSupportedException {
        ResourcePathResolver resourcePathResolver=new ResourcePathResolver();
        AWSApplicationDatabaseFile=resourcePathResolver.getPathOfFile(file);
        this.webServerId=webServerId;
        setUp();
    }

    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> data1() {
        return Arrays.asList(new Object[][]{
                {"JBossDatabaseApp", ResourcePathResolver.AWS_APPLICATION_DATABASE_SAMPLE_JBOSS,
                        "JBossMainWebServer"},
                {"JettyDatabaseApp", ResourcePathResolver.AWS_APPLICATION_DATABASE_SAMPLE_JETTY,
                        "JettyMainWebServer"},
                {"TomcatDatabaseApp", ResourcePathResolver.AWS_APPLICATION_DATABASE_SAMPLE_TOMCAT,
                        "TomcatMainWebServer"}
        });
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatabaseGraphGenerationTest.class);
    }

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
        Assert.assertNotNull(agnosticGraph);
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
        Assert.assertEquals(independentAgnosticComponents.size(), 2);
        Assert.assertNotNull(AgnosticComponentUtils
                .findAgnosticComponentById(independentAgnosticComponents, webServerId));
        Assert.assertNotNull(AgnosticComponentUtils
                .findAgnosticComponentById(independentAgnosticComponents, "MainMySql"));
    }
}
