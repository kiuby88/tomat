package org.tomat.tosca.parser.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.tosca.parsers.ToscaSupportedTypeProvider;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jose on 06/10/14.
 */
public class AppDatbaseParsingTest {

    List<TNodeTemplate> nodeTemplateListAWSDbSample;
    DefinitionParser definitionParser;
    String AWSApplicationDatabaseFile = "resources/AWS-Application-DatabaseSample.xml";

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AppDatbaseParsingTest.class);
    }

    @Before
    public void setUp() throws TopologyTemplateFormatException {
        nodeTemplateListAWSDbSample = DefinitionUtils.getNodeTemplates(new File(AWSApplicationDatabaseFile));
        definitionParser = new DefinitionParser();
    }

    @Test
    public void nodeTemplateAWSSampleNumber() {
        int numberOfNodeTemplates = 4;
        assertEquals(nodeTemplateListAWSDbSample.size(), numberOfNodeTemplates);
    }

    @Test
    public void checkTypesTest() {
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(0)).toLowerCase(),
                ToscaSupportedTypeProvider.JBOSS_WEB_SERVER.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(1)).toLowerCase(),
                ToscaSupportedTypeProvider.WEB_APPLICATION.toLowerCase());
        assertEquals(DefinitionUtils.getTypeName(nodeTemplateListAWSDbSample.get(2)).toLowerCase(),
                ToscaSupportedTypeProvider.MySQL_DBMS.toLowerCase());
    }

    @Test
    public void checkRelations()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException {
        definitionParser.parsingApplicationTopology(AWSApplicationDatabaseFile).buildAgnosticsElements();
        Map<String, List<String>> relationMaps = definitionParser.getAgnosticApplicationsComponentRelations();

        int numberOfRelationShipKeys = 2;
        assertEquals(relationMaps.size(), numberOfRelationShipKeys);
        //TODO refactor the next lines using independient methods
        //first
        assertEquals(relationMaps.containsKey("MainWebApp".toLowerCase()), true);
        assertEquals(relationMaps.get("MainWebApp".toLowerCase())
                .contains("JBossMainWebServer".toLowerCase()), true);

        //second
        assertEquals(relationMaps.containsKey("MainDB".toLowerCase()), true);
        assertEquals(relationMaps.get("MainDB".toLowerCase())
                .contains("MainMySql".toLowerCase()), true);

        //third
        assertEquals(relationMaps.containsKey("MainWebApp".toLowerCase()), true);
        assertEquals(relationMaps.get("MainWebApp".toLowerCase())
                .contains("MainDB".toLowerCase()), true);
    }
}
