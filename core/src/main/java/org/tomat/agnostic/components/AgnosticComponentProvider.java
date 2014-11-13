package org.tomat.agnostic.components;


import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.tosca.parsers.ToscaSupportedTypeProvider;

/**
 * Created by kiuby88 on 24/09/2014.
 */
public class AgnosticComponentProvider {

    /**
     * Fabric method.
     *
     * @param nodeTemplate
     * @return
     * @throws org.tomat.exceptions.NodeTemplateTypeNotSupportedException
     */
    public static AgnosticComponent createAgnosticComponent(TNodeTemplate nodeTemplate)
            throws NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        AgnosticComponent agnosticComponent;
        String nodeTemplateType = getTypeName(nodeTemplate).toLowerCase();

        //TODO Using fabric ot another pattert to delete this "SWITCH".
        if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.JBOSS_WEB_SERVER))
            agnosticComponent = new JBossAgnosticComponent(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.JETTY_WEB_SERVER))
            agnosticComponent = new JettyAgnosticComponent(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.TOMCAT_WEB_SERVER))
            agnosticComponent = new TomcatAgnosticComponent(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.WEB_APPLICATION))
            agnosticComponent = new WebAppAgnosticComponent(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.MySQL_DBMS))
            agnosticComponent = new MySQLAgnosticComponent(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.MySQL_DB))
            agnosticComponent = new MySQLDataBaseAgnosticComponent(nodeTemplate);

        else
            throw new NodeTemplateTypeNotSupportedException("Type: " + nodeTemplateType
                    + " not supported yet.");
        return agnosticComponent;
    }

    private static String getTypeName(TNodeTemplate nodeTemplate) {
        return DefinitionUtils.getTypeName(nodeTemplate);
    }

}
