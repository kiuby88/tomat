import org.jdom2.JDOMException;
import org.tomat.org.tomat.exceptions.TopologyTemplateFormatException;
import org.tomat.toscaParsers.ToscaServiceTemplateParser;

import java.io.IOException;

/**
 * Created by MariaC on 23/09/2014.
 */
public class main {


    public static void main(String[] args) throws JDOMException, IOException, TopologyTemplateFormatException {

        String xmlSource = "OnlineRetailingDefinitions-cloud-topology-v2.0.xml";
        ToscaServiceTemplateParser tSP=new ToscaServiceTemplateParser();
        tSP.topologyApplicationParsing(xmlSource);


    }



}
