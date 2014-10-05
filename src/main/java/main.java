import org.jdom2.JDOMException;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.DefinitionParser;
import org.tomat.tosca.parsers.NodeTemplateParser;
import org.tomat.tosca.parsers.NodeTemplateParserProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by MariaC on 23/09/2014.
 */
public class main {


    public static void main(String[] args) throws JDOMException, IOException, TopologyTemplateFormatException {

        List<TNodeTemplate> l= DefinitionUtils.getNodeTemplates(new File("resources/AWS-Location-Sample.xml"));
        TNodeTemplate nodeTemplateAWS=l.get(0);
        NodeTemplateParser nodeTemplateParser= NodeTemplateParserProvider.createNodeTemplateParser(nodeTemplateAWS);


    }



}
