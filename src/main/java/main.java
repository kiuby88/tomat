import org.jdom2.JDOMException;
import org.tomat.org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.tosca.parsers.ServiceTemplateParser;

import java.io.IOException;

/**
 * Created by MariaC on 23/09/2014.
 */
public class main {


    public static void main(String[] args) throws JDOMException, IOException, TopologyTemplateFormatException {

        String xmlSource = "OnlineRetailingDefinitions-cloud-topology-v2.0.xml";
        ServiceTemplateParser tSP=new ServiceTemplateParser();
        tSP.topologyApplicationParsing(xmlSource);


    }



}
