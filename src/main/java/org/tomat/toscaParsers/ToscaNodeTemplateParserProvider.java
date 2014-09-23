package org.tomat.toscaParsers;

import org.jdom2.Element;

/**
 * Created by MariaC on 24/09/2014.
 */
//Fabric method to describe the element
public class ToscaNodeTemplateParserProvider {

   public ToscaNodeTemplate createNodeTemplate(Element nodeTemplateDomElement){


       return new ToscaNodeTemplate();
   }

    private String getToscaNodeTemplateType(Element nodeTemplateDomElement){

    }
}
