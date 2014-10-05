package org.tomat.tosca.parsers;


import org.jdom2.Element;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;

/**
 * Created by MariaC on 24/09/2014.
 */
public class NodeTemplateParserProvider {



    public NodeTemplateParserProvider(){
    }

   //Fabric method to describe the element
   public static NodeTemplateParser createNodeTemplateParser(TNodeTemplate nodeTemplate){
       NodeTemplateParser nodeTemplateParser =null;
       String nodeTemplateType=getTypeName(nodeTemplate).toLowerCase();
       //TODO hay que meter el template en el parser
       if (nodeTemplateType.equals(ToscaSupportedTypeProvider.JBOSS_WEB_SERVER.toLowerCase())){
           nodeTemplateParser =new NodeTemplateParser(nodeTemplate);}

       return nodeTemplateParser;
   }

   private static String getTypeName(TNodeTemplate nodeTemplate){

       return DefinitionUtils.getTypeName(nodeTemplate);
   }

}
