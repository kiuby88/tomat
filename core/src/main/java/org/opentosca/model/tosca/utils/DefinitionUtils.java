package org.opentosca.model.tosca.utils;

import org.opentosca.model.tosca.ObjectFactory;
import org.opentosca.model.tosca.TArtifactTemplate;
import org.opentosca.model.tosca.TDefinitions;
import org.opentosca.model.tosca.TDeploymentArtifact;
import org.opentosca.model.tosca.TDeploymentArtifacts;
import org.opentosca.model.tosca.TEntityTemplate;
import org.opentosca.model.tosca.TExtensibleElements;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TNodeTypeImplementation;
import org.opentosca.model.tosca.TRelationshipTemplate;
import org.opentosca.model.tosca.TServiceTemplate;
import org.opentosca.model.tosca.TTopologyTemplate;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jose on 04/10/14.
 */

//TODO this class is too large and complicate because it should be called sometimes for obtaining all topology information.
//TODO An instanciable class should be created for containing all Template information
public class DefinitionUtils {

    private static Unmarshaller unmarshaller = null;


    private static void buildUnmarshaller() {
        try {
            if (unmarshaller == null) {
                JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
                unmarshaller = context.createUnmarshaller();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TDefinitions getDefinitions(File f) {
        buildUnmarshaller();
        TDefinitions tDefinition = null;
        try {
            tDefinition = (TDefinitions) unmarshaller.unmarshal(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tDefinition;
    }

    public static TServiceTemplate getServiceTemplate(TDefinitions tDefinitions) {
        TServiceTemplate serviceTemplate = null;
        for (TExtensibleElements element : tDefinitions.getServiceTemplateOrNodeTypeOrNodeTypeImplementation()) {
            if (element instanceof TServiceTemplate)
                return (TServiceTemplate) element;
        }
        return serviceTemplate;
    }

    public static TServiceTemplate getServiceTemplate(File file) {
        return getServiceTemplate(getDefinitions(file));
    }

    public static TTopologyTemplate getTopology(TServiceTemplate serviceTemplate) {
        return serviceTemplate.getTopologyTemplate();
    }

    public static TTopologyTemplate getTopologyTemplate(File f) {
        return getTopology(getServiceTemplate(getDefinitions(f)));
    }

    public static List<TNodeTemplate> getNodeTemplates(TTopologyTemplate topology) {
        return topology.getNodeTempaltes();
    }

    public static List<TRelationshipTemplate> getRelationshipTemplates(TTopologyTemplate topology) {
        return topology.getRelationshipTempaltes();
    }

    public static List<TNodeTemplate> getNodeTemplates(File f) {
        return getNodeTemplates(getTopology(getServiceTemplate(getDefinitions(f))));
    }

    public static List<TNodeTemplate> getNodeTemplates(TDefinitions definitions) {
        return getNodeTemplates(getTopology(getServiceTemplate(definitions)));
    }

    public static List<TRelationshipTemplate> getRelationshipTemplates(File f) {
        return getRelationshipTemplates(getTopology(getServiceTemplate(getDefinitions(f))));
    }

    public static List<TRelationshipTemplate> getRelationshipTemplates(TDefinitions definitions) {
        return getRelationshipTemplates(getTopology(getServiceTemplate(definitions)));
    }

    public static String getTypeName(TNodeTemplate nodeTemplate) {
        return nodeTemplate.getType().getLocalPart();
    }

    public static Map<String, String> getProperties(TEntityTemplate template) {
        Map<String, String> propertyMap = null;
        TEntityTemplate.Properties tproperties = template.getProperties();
        if (tproperties != null) {
            Element el = (Element) tproperties.getAny();
            if (el != null) {
                NodeList childNodes = el.getChildNodes();
                propertyMap = getChildAsMap(childNodes);
            }
        }
        return propertyMap;
    }

    private static Map<String, String> getChildAsMap(NodeList childNodes) {
        Map<String, String> propertyMap = new HashMap<String, String>();
        if (childNodes != null) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item instanceof Element) {
                    propertyMap.put(item.getLocalName(), item.getTextContent());
                }
            }
        }
        return propertyMap;
    }

    public static List<TNodeTypeImplementation> getTNodeTypeImplementations(TDefinitions definition) {
        List<TNodeTypeImplementation> nodeTypeImplementations =
                new LinkedList<TNodeTypeImplementation>();
        for (TExtensibleElements extensibleElement :
                definition.getServiceTemplateOrNodeTypeOrNodeTypeImplementation()) {
            if (extensibleElement instanceof TNodeTypeImplementation) {
                nodeTypeImplementations.add((TNodeTypeImplementation) extensibleElement);
            }
        }
        return nodeTypeImplementations;
    }

    public static List<TArtifactTemplate> getArtifactTemplates(TDefinitions definition) {

        List<TArtifactTemplate> artifactTemplates =
                new LinkedList<TArtifactTemplate>();
        for (TExtensibleElements extensibleElement :
                definition.getServiceTemplateOrNodeTypeOrNodeTypeImplementation()) {
            if (extensibleElement instanceof TArtifactTemplate) {
                artifactTemplates.add((TArtifactTemplate) extensibleElement);
            }
        }
        return artifactTemplates;
    }

    public static List<TDeploymentArtifact> getDeploymentArtifacts(TDefinitions definitions) {
        List<TDeploymentArtifact> result = new LinkedList<TDeploymentArtifact>();
        List<TNodeTypeImplementation> nodeTypeImplementations =
                getTNodeTypeImplementations(definitions);

        for (TNodeTypeImplementation nodeTypeImplementation : nodeTypeImplementations) {
            result.addAll(getDeploymentArtifacts(nodeTypeImplementation));
        }
        return result;
    }

    public static List<TDeploymentArtifact> getDeploymentArtifact(TDefinitions definitions, TNodeTemplate nodeTemplate) {
        TNodeTypeImplementation nodeTypeImplementation = getTNodeTypeImplementation(definitions, nodeTemplate);
        return getDeploymentArtifacts(nodeTypeImplementation);
    }

    public static TArtifactTemplate getArtifactTemplate(TDefinitions definitions, TDeploymentArtifact deploymentArtifact) {
        TArtifactTemplate result = null;
        for (TArtifactTemplate artifactTemplate : getArtifactTemplates(definitions)) {
            if (artifactTemplate.getId()
                    .equals(deploymentArtifact.getArtifactRef().getLocalPart())) {
                return artifactTemplate;
            }
        }
        return result;
    }

    public static List<TDeploymentArtifact> getDeploymentArtifacts(TNodeTypeImplementation nodeTypeImplementation) {
        List<TDeploymentArtifact> result=null;
        if(nodeTypeImplementation!=null){
            result= getDeploymentArtifacts(nodeTypeImplementation.getDeploymentArtifacts());
        }
        return result;
    }

    public static List<TDeploymentArtifact> getDeploymentArtifacts(TDeploymentArtifacts deploymentArtifacts) {
        List<TDeploymentArtifact> result = null;
        if ((deploymentArtifacts != null)
                && (!deploymentArtifacts.getDeploymentArtifact().isEmpty())) {
            result = deploymentArtifacts.getDeploymentArtifact();
        }
        return result;
    }

    public static TNodeTypeImplementation getTNodeTypeImplementation(TDefinitions definitions, TNodeTemplate nodeTemplate) {
        TNodeTypeImplementation result = null;
        for (TNodeTypeImplementation tNodeTypeImplementation : getTNodeTypeImplementations(definitions)) {
            if (isItsTNodeTypeImplementation(nodeTemplate, tNodeTypeImplementation)) {
                return tNodeTypeImplementation;
            }
        }
        return result;
    }

    public static boolean isItsTNodeTypeImplementation(TNodeTemplate nodeTemplate, TNodeTypeImplementation nodeTypeImplementation) {
        return nodeTemplate.getType().getLocalPart()
                .equalsIgnoreCase(nodeTypeImplementation.getNodeType().getLocalPart());

    }


}
