package org.tomat.agnostic.application.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.tomat.ResourcePathResolver;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.agnostic.application.ApplicationAgnosticMetadata;
import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.components.AgnosticComponentUtils;
import org.tomat.agnostic.graphs.AgnosticGraph;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kiuby88 on 15/10/14.
 */

@RunWith(value = Parameterized.class)
public class AppDatabaseAgnosticApplicationTest {

    ToscaProcessor toscaProcessor;
    String AWSApplicationDatabaseFile;
    AgnosticApplication agnosticApplication;
    String webServerId;
    private AgnosticGraph agnosticGraph;

    public AppDatabaseAgnosticApplicationTest(String name, String file, String webServerId)
            throws AgnosticPropertyException, TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException {
        ResourcePathResolver resourcePathResolver=new ResourcePathResolver();
        this.AWSApplicationDatabaseFile = resourcePathResolver.getPathFile(file);
        this.webServerId = webServerId;
        setUp();
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatabaseAgnosticApplicationTest.class);
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

    public void setUp() throws TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        toscaProcessor = new ToscaProcessor();
        toscaProcessor
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticApplication = new AgnosticApplication(toscaProcessor);
        agnosticGraph = agnosticApplication.getAgnosticGraph();
    }

    @Test
    public void testCreation_definitionEmpty() {
        agnosticApplication = new AgnosticApplication(new ToscaProcessor());
        Assert.assertNotNull(agnosticApplication);
        Assert.assertNotNull(agnosticApplication.getAgnosticGraph());
    }

    @Test
    public void testMetadata_definitionEmpty() {
        agnosticApplication = new AgnosticApplication(new ToscaProcessor());
        ApplicationAgnosticMetadata metadata = agnosticApplication.getAgnosticMetadata();
        assertEquals(metadata.getId(), ApplicationAgnosticMetadata.APPLICATION_ID_BY_DEFAULT);
        assertEquals(metadata.getName(), ApplicationAgnosticMetadata.APPLICATION_NAME_BY_DEFAULT);
    }

    @Test
    public void testMetadata() {
        ApplicationAgnosticMetadata metadata =
                agnosticApplication.getAgnosticMetadata();

        assertEquals(metadata.getId(), "dbApp");
        assertEquals(metadata.getName(), "DatabaseApp");
    }

    @Test
    public void testNumberOfVertex() {
        assertEquals(agnosticApplication.getAgnosticGraph().getVertexSet().size(), 4);
    }

    @Test
    public void testRelations_OutgoingEdges() {
        List<AgnosticComponent> independentAgnosticComponents = agnosticGraph.getIndependentVertex();
        Assert.assertEquals(independentAgnosticComponents.size(), 2);
        Assert.assertNotNull(AgnosticComponentUtils
                .findAgnosticComponentById(independentAgnosticComponents, webServerId));
        Assert.assertNotNull(AgnosticComponentUtils
                .findAgnosticComponentById(independentAgnosticComponents, "MainMySql"));
    }
}
