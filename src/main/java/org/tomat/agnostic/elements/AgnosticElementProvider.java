package org.tomat.agnostic.elements;


import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.tosca.parsers.ToscaSupportedTypeProvider;

/**
 * Created by MariaC on 24/09/2014.
 */
public class AgnosticElementProvider {

    /**
     * Fabric method.
     *
     * @param nodeTemplate
     * @return
     * @throws NodeTemplateTypeNotSupportedException
     */
    public static AgnosticElement createAgnosticElement(TNodeTemplate nodeTemplate)
            throws NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        AgnosticElement agnosticElement;
        String nodeTemplateType = getTypeName(nodeTemplate).toLowerCase();

        if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.JBOSS_WEB_SERVER))
            agnosticElement = new JBossAgnosticElement(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.WEB_APPLICATION))
            agnosticElement = new AgnosticElement(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.MySQL_DBMS))
            agnosticElement = new MySQLAgnosticElement(nodeTemplate);

        else if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.MySQL_DB))
            agnosticElement = new MySQLDataBaseAgnosticElement(nodeTemplate);

        else
            throw new NodeTemplateTypeNotSupportedException("Type: " + nodeTemplateType
                    + " not supported yet.");
        return agnosticElement;
    }

    private static String getTypeName(TNodeTemplate nodeTemplate) {
        return DefinitionUtils.getTypeName(nodeTemplate);
    }

}
