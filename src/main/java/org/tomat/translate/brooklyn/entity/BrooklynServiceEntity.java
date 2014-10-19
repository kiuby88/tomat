package org.tomat.translate.brooklyn.entity;

import org.tomat.agnostic.elements.AgnosticElement;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.brooklyn.property.BrooklynProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jose on 18/10/14.
 */
//TODO comprobar si la paquetería se hace normalmente en plural
//TODO o en singular
public abstract class BrooklynServiceEntity extends BrooklynEntity implements TechnologyComponent {

    private Map<String, Object> brooklinConfigProperties;
    private AgnosticElement agnosticElement;

    public BrooklynServiceEntity(AgnosticElement agnosticElement){
        super(generateBuilder(agnosticElement));
        this.agnosticElement=agnosticElement;
        initBrooklynConfigProperties();
    }

    private static Builder generateBuilder(AgnosticElement agnosticElement){
        return new BrooklynEntity.Builder()
                .id(agnosticElement.getId())
                .name(agnosticElement.getName())
                .location(agnosticElement.getLocation());
    }

    //TODO refactor this name
    public Map<Class<? extends AgnosticProperty>, String> getSupportedAgnosticPropertiesAndBrooklynPropertyId(){
        return new HashMap<>();
    }

    private void initBrooklynConfigProperties(){
        brooklinConfigProperties=new HashMap<>();
        List<AgnosticProperty> propertiesOfAgnosticElement=
                agnosticElement.getProperties();
        for(AgnosticProperty agnosticProperty : propertiesOfAgnosticElement){
            addSupportedProperty(agnosticProperty);
        }
    }

    private void addSupportedProperty(AgnosticProperty agnosticProperty){
        if(checkIsSupported(agnosticProperty)){
            BrooklynProperty brooklynProperty= getBrooklynPropertyFabric(agnosticProperty);
            this.brooklinConfigProperties
                    .put(brooklynProperty.getId(), brooklynProperty.getValue());
        }
    }

    private boolean checkIsSupported(AgnosticProperty agnosticProperty){
        return getSupportedAgnosticPropertiesAndBrooklynPropertyId()
                .keySet()
                .contains(agnosticProperty.getClass());
    }
    //TODO rename this method because we do not need the fabric, we need a
    //TODO property translation
    private BrooklynProperty getBrooklynPropertyFabric(AgnosticProperty agnosticProperty){
        //TODO Add a fabric of properties, it is important because currently
        //TODO the elements are created
        return new BrooklynProperty(agnosticProperty.getId(), agnosticProperty.getValue());
    }

    @Override
    public AgnosticElement getAgnosticElement(){
        return agnosticElement;
    }

    public abstract String getServiceType();
}
