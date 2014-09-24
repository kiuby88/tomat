package org.tomat.tosca.parsers;


import org.jdom2.Element;

/**
 * Created by MariaC on 24/09/2014.
 */
public class NodeTemplateParserProvider {



    public NodeTemplateParserProvider(){
    }

   //Fabric method to describe the element
   public NodeTemplateParser createNodeTemplateParser(Element nodeTemplateDomElement){
       NodeTemplateParser nodeTemplateParser =null;
       String nodeTemplateType=getNodeTemplateType(nodeTemplateDomElement).toLowerCase();
       if (nodeTemplateType.equals(ToscaSupportedTypeProvider.JBOSS_WEB_SERVER.toLowerCase()))
           nodeTemplateParser =new JBossNodeTemplateParser();
       return nodeTemplateParser;
   }

    private String getNodeTemplateType(Element nodeTemplateDomElement){
        //TODO Element have to be a NodeTemplateElement
        String  st = nodeTemplateDomElement.getAttributeValue(NODE_TEMPLATE_TYPE_ATTRIBUTE);
        System.out.println("Atributo: -->" +st);
        return st;
    }
}
