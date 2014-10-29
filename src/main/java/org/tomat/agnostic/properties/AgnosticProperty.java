package org.tomat.agnostic.properties;

import org.tomat.agnostic.AgnosticElement;
import org.tomat.agnostic.components.AgnosticComponentUtils;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public abstract class AgnosticProperty implements AgnosticElement {
    private String id;
    private String value;

    public AgnosticProperty(Map<String, String> properties){
        initProperty(properties);
    }

    private void initProperty(Map<String, String> properties) {
        setId(findPropertyIdUsed(properties));
        if(getId()!=null){
            setValue(properties.get(id));
        }
    }

    private String findPropertyIdUsed(Map<String, String> properties){
        String[] allowed=getAllowedPropertyIds();
        return AgnosticComponentUtils
                .anyKeyFromCollectionIsAMapKey(allowed, properties);
    }

    public abstract String[] getAllowedPropertyIds();

    public boolean isCompleted(){
        return getValue()!=null;
    }

    //<editor-fold desc="Getters and Setters">
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
    //</editor-fold>




}


