package org.tomat.tosca.parsers;


import org.jdom2.Element;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;

/**
 * Created by MariaC on 24/09/2014.
 */
public class NodeTemplateParserProvider {

    /**
     * Fabric method.
     * @param nodeTemplate
     * @return
     * @throws NodeTemplateTypeNotSupportedException
     */
    public static NodeTemplateParser createNodeTemplateParser(TNodeTemplate nodeTemplate)
            throws NodeTemplateTypeNotSupportedException {
        NodeTemplateParser nodeTemplateParser;
        String nodeTemplateType = getTypeName(nodeTemplate).toLowerCase();

        if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.JBOSS_WEB_SERVER))
            nodeTemplateParser = new JBossNodeTemplateParser(nodeTemplate);

        else if(nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.WEB_APPLICATION))
            nodeTemplateParser=new NodeTemplateParser(nodeTemplate);

        else if(nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.MySQL_DBMS))
            nodeTemplateParser=new MySQLNodeTemplateParser(nodeTemplate);

        else if(nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.MySQL_DB))
            nodeTemplateParser=new MySQLDataBaseNodeTemplaterParser(nodeTemplate);

        else
            throw new NodeTemplateTypeNotSupportedException("Type: " + nodeTemplateType + " not supported yet.");
        return nodeTemplateParser;
    }

    private static String getTypeName(TNodeTemplate nodeTemplate) {
        return DefinitionUtils.getTypeName(nodeTemplate);
    }

}
