package org.tomat.tosca.parser.test;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;

/**
 * Created by Jose on 04/10/14.
 */

public class DefinitionParserTest2 {

    //TODO rename the methods using the methodology of Google JAva Style
    DefinitionParser definitionParser;
    String AWSDeploymentWithoutTemplate = "resources/AWS-DeploymentArtifactWithoutArtifactTemplate2.xml";


    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(DefinitionParserTest2.class);
    }


    @Test(expected = TopologyTemplateFormatException.class)
    public void testAgnosticComponent_NotArtifactTemplate()
            throws NodeTemplateTypeNotSupportedException, TopologyTemplateFormatException,
            AgnosticPropertyException {
        definitionParser=new DefinitionParser();
        definitionParser
                .parsingApplicationTopology(AWSDeploymentWithoutTemplate)
                .buildAgnostics();
    }
}
