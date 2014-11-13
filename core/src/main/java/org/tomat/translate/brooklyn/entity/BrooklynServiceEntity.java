package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.components.AgnosticComponent;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.brooklyn.property.BrooklynProperty;
import org.tomat.translate.brooklyn.property.BrooklynPropertyProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kiuby88 on 18/10/14.
 */
//TODO comprobar si la paquetería se hace normalmente en plural
//TODO o en singular
public abstract class BrooklynServiceEntity extends BrooklynEntity implements TechnologyComponent {

    private final static String TYPE="IT_IS_NECESSARY_To_SPECIFY_A_TYPE";

    private Map<String, Object> brooklynConfigProperties;
    private AgnosticComponent agnosticComponent;
    private String serviceType;

    public BrooklynServiceEntity(AgnosticComponent agnosticComponent){
        super(generateBuilder(agnosticComponent));
        this.agnosticComponent = agnosticComponent;
        setServiceType(TYPE);
        initBrooklynConfigProperties();
    }

    private static Builder generateBuilder(AgnosticComponent agnosticComponent){
        return new BrooklynEntity.Builder()
                .id(agnosticComponent.getId())
                .name(agnosticComponent.getName())
                .location(agnosticComponent.getLocation());
    }


    public Map<Class<? extends AgnosticProperty>, String> getSupportedAgnosticAndBrooklynPropertyId(){
        return new HashMap<>();
    }

    private void initBrooklynConfigProperties(){
        setBrooklynConfigProperties(new HashMap<String, Object>());
        List<AgnosticProperty> propertiesOfAgnosticComponent=
                agnosticComponent.getProperties();
        for(AgnosticProperty agnosticProperty : propertiesOfAgnosticComponent){
            addSupportedProperty(agnosticProperty);
        }
    }

    private void addSupportedProperty(AgnosticProperty agnosticProperty){
        if(checkIsSupported(agnosticProperty)){
            BrooklynProperty brooklynProperty= buildBrooklynProperty(agnosticProperty);
            String technologyComponentPropertyId=
                    getSupportedAgnosticAndBrooklynPropertyId()
                            .get(agnosticProperty.getClass());

            this.addConfigProperty(technologyComponentPropertyId, brooklynProperty.getValue());
        }
    }

    private boolean checkIsSupported(AgnosticProperty agnosticProperty){
        return getSupportedAgnosticAndBrooklynPropertyId()
                .keySet()
                .contains(agnosticProperty.getClass());
    }

    //TODO this asume only a level, if we have more level we could add an recursive method based
    //TODOin the iterable property
    public void addConfigProperty(String agnosticProperty, Object value){
        if(value!=null){
            this.getBrooklynConfigProperties().put(agnosticProperty,value);
        }
    }

    //TODO rename this method because we do not need the fabric, we need a
    //TODO property translation
    private BrooklynProperty buildBrooklynProperty(AgnosticProperty agnosticProperty){
        //TODO Add a fabric of properties, it is important because currently
        //TODO the component are created
        return BrooklynPropertyProvider.createBrooklynProperty(agnosticProperty);
    }

    @Override
    public AgnosticComponent getAgnosticComponent(){
        return agnosticComponent;
    }

    public String getServiceType(){
        return serviceType;
    }

    public void setServiceType(String serviceType){
        this.serviceType=serviceType;
    }

    public Map<String, Object> getBrooklynConfigProperties() {
        return brooklynConfigProperties;
    }

    public void setBrooklynConfigProperties(Map<String, Object> brooklynConfigProperties) {
        this.brooklynConfigProperties =new HashMap<>();

        if(brooklynConfigProperties !=null){
            for(String propertyId : brooklynConfigProperties.keySet()){
                addConfigProperty(propertyId, brooklynConfigProperties.get(propertyId));
            }
        }
    }


}
