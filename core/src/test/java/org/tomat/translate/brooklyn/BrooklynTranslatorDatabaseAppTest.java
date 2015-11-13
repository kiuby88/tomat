package org.tomat.translate.brooklyn;

/**
 * Created by Jose on 20/10/14.
 */

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.tomat.ResourcePathResolver;
import org.tomat.agnostic.application.AgnosticApplication;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ToscaProcessor;
import org.tomat.translate.brooklyn.entity.BrooklynApplicationEntity;
import org.tomat.translate.brooklyn.entity.BrooklynEntity;
import org.tomat.translate.brooklyn.entity.BrooklynServiceEntity;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;
import org.tomat.translate.brooklyn.exceptions.BrooklynVisitorRelationConfigurationNotSupportedType;
import org.tomat.translate.exceptions.NotSupportedTypeByTechnologyException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Kiuby88 on 15/10/14.
 */
@RunWith(value = Parameterized.class)
public class BrooklynTranslatorDatabaseAppTest {

    ToscaProcessor toscaProcessor;
    String AWSApplicationDatabaseFile;
    String webServerId;
    AgnosticApplication agnosticApplication;
    BrooklynTranslator brooklynTranslator;
    BrooklynApplicationEntity brooklynApplicationEntity;
    List<BrooklynServiceEntity> services;
    String yamlFile;

    public BrooklynTranslatorDatabaseAppTest(String name, String file, String webServerId,
                                             String yamlFile)
            throws AgnosticPropertyException, TopologyTemplateFormatException,
            NodeTemplateTypeNotSupportedException, NotSupportedTypeByTechnologyException,
            BrooklynVisitorRelationConfigurationNotSupportedType {
        ResourcePathResolver resourcePathResolver= new ResourcePathResolver();
        AWSApplicationDatabaseFile=resourcePathResolver.getPathFile(file);
        this.webServerId=webServerId;
        this.yamlFile=resourcePathResolver.getPathFile(yamlFile);
        setUp();
    }

    //Declares parameters here
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> data1() {
        return Arrays.asList(new Object[][]{
                {"JBossDatabaseApp", ResourcePathResolver.AWS_APPLICATION_DATABASE_SAMPLE_JBOSS,
                        "JBossMainWebServer", ResourcePathResolver.APP_DB_JBOSS_YAML},
                {"JettyDatabaseApp", ResourcePathResolver.AWS_APPLICATION_DATABASE_SAMPLE_JETTY,
                        "JettyMainWebServer", ResourcePathResolver.APP_DB_JETTY_YAML},
                {"TomcatDatabaseApp", ResourcePathResolver.AWS_APPLICATION_DATABASE_SAMPLE_TOMCAT,
                        "TomcatMainWebServer", ResourcePathResolver.APP_DB_TOMCAT_YAML}
        });
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(BrooklynTranslatorDatabaseAppTest.class);
    }

    public void setUp()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException,
            AgnosticPropertyException, NotSupportedTypeByTechnologyException,
            BrooklynVisitorRelationConfigurationNotSupportedType {

        toscaProcessor =new ToscaProcessor();
        toscaProcessor
                .parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnostics();
        agnosticApplication = new AgnosticApplication(toscaProcessor);
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
        Assert.assertNotNull(brooklynApplicationEntity);
        assertEquals(brooklynApplicationEntity.getId(), "dbApp");
        assertEquals(brooklynApplicationEntity.getName(), "DatabaseApp");
        assertEquals(brooklynApplicationEntity.getLocation(), BrooklynEntity.LOCATION_BY_DEFAULT);
    }

    @Test
    public void testBrooklynTranslating_WebServer()
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {
        BrooklynServiceEntity webServerBrooklynService = services.get(0);
        Assert.assertNotNull(webServerBrooklynService.getBrooklynConfigProperties());
        assertEquals(webServerBrooklynService.getId(), webServerId);
        assertEquals(webServerBrooklynService.getBrooklynConfigProperties().size(), 3);
        assertEquals(webServerBrooklynService.getBrooklynConfigProperties().get("port.http"), "80+");
    }

    @Test
    public void testBrooklynTranslating_MySQL()
            throws AgnosticComponentTypeNotSupportedbyBrooklyException {

        BrooklynServiceEntity mySQL = services.get(1);
        Assert.assertNotNull(mySQL);
        Assert.assertNotNull(mySQL.getBrooklynConfigProperties());
        assertEquals(mySQL.getBrooklynConfigProperties().size(), 1);
    }

    @Test
    public void testBrooklynPrinting()
            throws AgnosticComponentTypeNotSupportedbyBrooklyException, IOException {

        brooklynApplicationEntity = brooklynTranslator.getBrooklynApplicationEntity();
        brooklynTranslator.print(yamlFile);
    }
}
