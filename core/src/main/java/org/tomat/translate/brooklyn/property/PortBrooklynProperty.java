package org.tomat.translate.brooklyn.property;

/**
 * Created by Jose on 20/10/14.
 */
public class PortBrooklynProperty extends BrooklynProperty {

    private final static String PORT_EXTENSION="+";

    public PortBrooklynProperty(String id, String value){
        super(id, value);
    }

    @Override
    public void setValue(String value){
        if(value!=null){
            super.setValue(addPortExtension(value));
        }
    }

    private String addPortExtension(String port){
        String result=null;
        if(port!=null){
            result=port+PORT_EXTENSION;
        }
        return result;
    }
}
