package org.tomat.agnostic.properties;

import org.tomat.agnostic.elements.AgnosticElementUtils;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public abstract class AgnosticProperty {
    private String id;
    private String value;

    public AgnosticProperty(String id, Map<String, String> properties){
        this.setId(id);
        initValue(properties);
    }

    private void initValue(Map<String, String> properties) {
        value=parsingRelation(properties);
    }

    private String  parsingRelation(Map<String, String > properties){
        return AgnosticElementUtils
                .findValueMapUsingCollection(properties, getAllowedPropertyIds());
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


