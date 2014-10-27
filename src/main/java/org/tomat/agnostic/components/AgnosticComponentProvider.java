package org.tomat.agnostic.components;


import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.exceptions.NodeTemplateTypeNotSupportedException;
import org.tomat.tosca.parsers.ToscaSupportedTypeProvider;

/**
 * Created by MariaC on 24/09/2014.
 */
public class AgnosticComponentProvider {

    /**
     * Fabric method.
     *
     * @param nodeTemplate
     * @return
     * @throws NodeTemplateTypeNotSupportedException
     */
    public static AgnosticComponent createAgnosticComponent(TNodeTemplate nodeTemplate)
            throws NodeTemplateTypeNotSupportedException, AgnosticPropertyException {
        AgnosticComponent agnosticComponent;
        String nodeTemplateType = getTypeName(nodeTemplate).toLowerCase();



        //TODO it could be interesting change this method using a switch and using
        //TODO types of the AgnosticComponents. Really, the TOSCA support class
        //TODO it is not used, because the components are specify using the Types
        //TODO And in any moments is necessary check if it is supported because,
        //TODO it are managed using the default method.
        //TODO In any case or use switch or using Google code style.
        if (nodeTemplateType.equalsIgnoreCase(ToscaSupportedTypeProvider.JBOSS_WEB_SERVER))
            agnosticComponent = new JBossAgnosticComponent(nodeTemplate);

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
