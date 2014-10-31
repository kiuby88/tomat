package org.tomat.translate.brooklyn.property;

import org.tomat.translate.TechnologyProperty;
import org.tomat.translate.brooklyn.BrooklynElement;

/**
 * Created by Kiuby88 on 18/10/14.
 */
public class BrooklynProperty implements BrooklynElement, TechnologyProperty {

    private String id;
    private String value;

    public BrooklynProperty(String id, String value){
        this.setId(id);
        this.setValue(value);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
