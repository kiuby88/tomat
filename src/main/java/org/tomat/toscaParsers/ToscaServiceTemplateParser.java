package org.tomat.toscaParsers;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.tomat.agnostic.AgnosticApplicationComponent;

import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;
import org.tomat.org.tomat.exceptions.TopologyTemplateFormatException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by JoseC on 23/09/2014.
 */
//TODO check the clean code to specifi <variableName>List (e.g.).
//Esta clase contendrá un metodo para parsear el fichero que contiene la topología
public class ToscaServiceTemplateParser {

    final static int NUMBER_OF_SERVICE_TEMPLATE_ALLOWED=1;
    final static int NUMBER_OF_TOPOLOGY_TEMPLATE_ALLOWED=1;
    final static String SERVICE_TEMPLATE="ServiceTemplate";
    final static String TOPOLOGY_TEMPLATE="TopologyTemplate";
    final static String NODE_TEMPLATE="NodeTemplate";

    LinkedList<AgnosticApplicationComponent> agnosticApplicationComponentList;

    public ToscaServiceTemplateParser(){
        agnosticApplicationComponentList=new LinkedList<AgnosticApplicationComponent>();
    }

    private Document buildDocument(String serviceTopologyFile) throws JDOMException, IOException {
        SAXBuilder jdomBuilder = new SAXBuilder();
        return jdomBuilder.build(serviceTopologyFile);
    }

    public void topologyApplicationParsing(String serviceTopologyFile) throws JDOMException, IOException, TopologyTemplateFormatException {
        Document jdomDoc = buildDocument(serviceTopologyFile);
        List<Element> nodeTemplates= getNodeTemplatesOfDom(jdomDoc);
        generateAgnosticApplicationComponentList(nodeTemplates);
    }

    private List<Element> getNodeTemplatesOfDom(Document jdomDoc) throws TopologyTemplateFormatException {
        Element topologyTemplateElement = getTopologyTemplateOfDom(jdomDoc);
        List<Element> nodeTemplateElements= getNodeTemplateListFromTopologyTemplate(topologyTemplateElement);
        System.out.println(nodeTemplateElements.size());
        return nodeTemplateElements;
    }

    private List<Element> getNodeTemplateListFromTopologyTemplate(Element topologyTemplate){
        /*TODO check if the topologyTemplate is really a TopologyTemplateElement.
          TODO If it is not a correct, a empty list will be returned. In any case, this have to
          TODO be described in the documentation of the method.
         */
        List<Element> nodeTemplateElements=topologyTemplate.getChildren(NODE_TEMPLATE, topologyTemplate.getNamespace());
        return nodeTemplateElements;
    }

    private Element getTopologyTemplateOfDom(Document jdomDoc) throws TopologyTemplateFormatException {
    //TODO delete the nameSpaceSupport
        Element serviceTemplateElement=getServiceTemplateOfDom(jdomDoc);
        List<Element> topologyTemplatesList=serviceTemplateElement
                .getChildren(TOPOLOGY_TEMPLATE, serviceTemplateElement.getNamespace());
        if(topologyTemplatesList.size()!=NUMBER_OF_TOPOLOGY_TEMPLATE_ALLOWED)
            throw new TopologyTemplateFormatException("Unexpected number of topology template elements in the Document: "+
                    topologyTemplatesList.size());
        return topologyTemplatesList.get(0);
    }

    private Element getServiceTemplateOfDom(Document jdomDoc) throws TopologyTemplateFormatException {
    //TODO delete the nameSpaceSupport
        Element root = jdomDoc.getRootElement();
        List<Element> serviceTemplatesList=root.getChildren(SERVICE_TEMPLATE, root.getNamespace());
        if(serviceTemplatesList.size()!=NUMBER_OF_SERVICE_TEMPLATE_ALLOWED)
            throw new TopologyTemplateFormatException("Unexpected number of service template elements in the Document: " +
                    serviceTemplatesList.size() );
        return serviceTemplatesList.get(0);
    }

    private void generateAgnosticApplicationComponentList(List<Element> nodeTemplates){
        //TODO generr todos los
    }



    private void parserNodeTemplates(Document jdomDoc) throws TopologyTemplateFormatException {
        Element root = jdomDoc.getRootElement();
        List<Element> serviceTemplateElementList = root.getChildren(SERVICE_TEMPLATE);
        if(serviceTemplateElementList.size()!=NUMBER_OF_SERVICE_TEMPLATE_ALLOWED){
            throw new TopologyTemplateFormatException("Unexpected number of service template in the");
        }
//        serviceTemplateElementList.get(0)



    }




}


//String xmlSource = "resourceOnlineRetailingDefinitions-cloud-topology-v2.0.xml";
//
//
//    public void OpenJdom() throws JDOMException, IOException {
//        SAXBuilder jdomBuilder = new SAXBuilder();
//
//        // jdomDocument is the JDOM2 Object
//        Document jdomDocument = jdomBuilder.build(xmlSource);
//
//        // The root element is the root of the document. we print its name
//        System.out.println(jdomDocument.getRootElement().getName());
//
//    }

