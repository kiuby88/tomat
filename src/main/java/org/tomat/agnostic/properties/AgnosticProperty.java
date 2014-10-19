package org.tomat.agnostic.properties;

import org.tomat.agnostic.Agnostic;
import org.tomat.agnostic.elements.AgnosticElementUtils;

import java.util.Map;

/**
 * Created by Jose on 17/10/14.
 */
public abstract class AgnosticProperty implements Agnostic{
    private String id;
    private String value;

    public AgnosticProperty(Map<String, String> properties){
        initProperty(properties);
    }



    private void initProperty(Map<String, String> properties) {
        id=findPropertyIdUsed(properties);
        if(id!=null){
            value=properties.get(id);
        }
    }

    private String findPropertyIdUsed(Map<String, String> properties){
        return AgnosticElementUtils
                .anyKeyFromCollectionIsAMapKey(getAllowedPropertyIds(), properties);
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


