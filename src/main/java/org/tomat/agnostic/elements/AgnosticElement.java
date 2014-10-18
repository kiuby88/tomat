package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TCapability;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TRequirement;
import org.opentosca.model.tosca.utils.DefinitionUtils;

import org.tomat.agnostic.Agnostic;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Created by MariaC on 24/09/2014.
 */
public class AgnosticElement implements Agnostic {

    private static final String DEFAULT_LOCATION = "localhost";
    private static final Class<?> ROOT_AGNOSTIC_PROPERTY_CLASS = AgnosticProperty.class;

    private String id;
    private String name;
    private String type;
    private String location;
    private TNodeTemplate sourceNodeTemplate;
    private List<AgnosticProperty> properties;
    private List<String> capabilitiesIds;
    private List<String> requirementsIds;

    public AgnosticElement() {
    }

    public AgnosticElement(String id) {
        this.id = id;
    }

    public AgnosticElement(TNodeTemplate nodeTemplate) throws AgnosticPropertyException {
        sourceNodeTemplate = nodeTemplate;
        initGenericValues();
        initCapabilitiesIds();
        initRequirementsIds();
        initProperties();
    }

    private void initGenericValues() {
        if (sourceNodeTemplate != null) {
            id = sourceNodeTemplate.getId();
            name = sourceNodeTemplate.getName();
            type = DefinitionUtils.getTypeName(sourceNodeTemplate);
            setLocation(sourceNodeTemplate);
        }
    }

    private void setLocation(TNodeTemplate nodeTemplate) {
        if (locationIsEmpty(nodeTemplate))
            location = DEFAULT_LOCATION;
        else
            location = nodeTemplate.getLocation().getLocationId();
    }

    private boolean locationIsEmpty(TNodeTemplate nodeTemplate) {
        return ((nodeTemplate.getLocation() == null)
                || (nodeTemplate.getLocation().getLocationId() == null)
                || (nodeTemplate.getLocation().getLocationId().equals("")));
    }

    //TODO could be interesting delete this element of the agnostic Elements
    private void initCapabilitiesIds() {
        List<TCapability> capabilitiesOfSourceNodeTemplate;
        if ((sourceNodeTemplate != null)
                && (sourceNodeTemplate.getCapabilities() != null)) {
            capabilitiesIds = new LinkedList<>();
            capabilitiesOfSourceNodeTemplate = sourceNodeTemplate.getCapabilities().getCapability();
            for (TCapability capability : capabilitiesOfSourceNodeTemplate) {
                capabilitiesIds.add(capability.getId());
            }
        }
    }

    //TODO could be interesting delete this element of the agnostic Elements
    private void initRequirementsIds() {
        List<TRequirement> requirementsOfSourceNodeTemplate;
        if ((sourceNodeTemplate != null)
                && (sourceNodeTemplate.getRequirements() != null)) {
            requirementsIds = new LinkedList<>();
            requirementsOfSourceNodeTemplate = sourceNodeTemplate.getRequirements().getRequirement();
            for (TRequirement requirement : requirementsOfSourceNodeTemplate) {
                requirementsIds.add(requirement.getId());
            }
        }
    }

    private void initProperties()
            throws AgnosticPropertyException {

        Map<String, Class<?>> expectedProperties = this.getExpectedProperties();
        Set<String> propertyIdsOfAgnosticElement = expectedProperties.keySet();
        Class expectedPropertySpecification;

        setProperties(new LinkedList<AgnosticProperty>());
        for (String expectedPropertyId : propertyIdsOfAgnosticElement) {
            expectedPropertySpecification = expectedProperties.get(expectedPropertyId);
            addProperty(expectedPropertyId, expectedPropertySpecification);
        }
    }

    private void addProperty(String id, Class<?> propertyClass) throws AgnosticPropertyException {
        try {

            if(checkClassIsAgnosticDefinitionClass(propertyClass))
                throw new AgnosticPropertyException("Class "+propertyClass.getName()
                        +" is not a AgnosticPropertyDefinition");

            AgnosticProperty agnosticProperty = (AgnosticProperty) propertyClass
                    .getConstructor(String.class, Map.class)
                    .newInstance(id, getNodeTemplateProperties());
            addProperty(agnosticProperty);

        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            throw new AgnosticPropertyException("Problem  instantiation: " + propertyClass.getName()
                    + ". Original Exception" + e.getMessage());
        }
    }

    private void addProperty(AgnosticProperty property) {
        getProperties().add(property);
    }

    private boolean checkClassIsAgnosticDefinitionClass(Class<?> propertyClass) {
        return propertyClass.isAssignableFrom(ROOT_AGNOSTIC_PROPERTY_CLASS);
    }

    private void clearNotCompletedProperties(){
        /*
        initProperties initializes the properties using null for properties
        which were not defined in the TOSCA, and they have not a value.
        Although the no-completed properties could be cleared by the
        AgnosticElements, a final technology could need the fully agnostic
        properties of an agnostic element. So, the charge of purge the
        completed and not completed properties will be the concrete-builder
        of any technology.
         */
        for(AgnosticProperty property: getProperties()){
            if(!property.isCompleted()){
                deleteProperty(property);
            }
        }
    }

    private void deleteProperty(AgnosticProperty property){
        if(getProperties().contains(property)){
            getProperties().remove(property);
        }
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof AgnosticElement) {
            AgnosticElement that = (AgnosticElement) o;
            result = this.getId().equalsIgnoreCase(that.getId());
        }
        return result;
    }

    //TODO probar este metodo en package visibility to check that only the package classes are able to use it
    public Map<String, Class<?>> getExpectedProperties() {
        return new HashMap<>();
    }

    //TODO este metodo es demasiado pesado, habría que cambiarlo para que no siempre se tenga que llamar al metodo que
    //construye el mapa en lowercase
    //TODO optimice
    Map<String, String> getNodeTemplateProperties() {
        return AgnosticElementUtils.putLowerCaseMapKeys(DefinitionUtils.getProperties(this.getNodeTemplate()));
    }

    // <editor-fold desc="Getters and Setters">
    public List<String> getCapabilitiesIds() {
        return capabilitiesIds;
    }

    public List<String> getRequirementsIds() {
        return requirementsIds;
    }

    public TNodeTemplate getNodeTemplate() {
        return sourceNodeTemplate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if ((location == null) || (location.equals(""))) {
            this.location = DEFAULT_LOCATION;
        } else {
            this.location = location;
        }
    }

    public List<AgnosticProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<AgnosticProperty> properties) {
        this.properties = properties;
    }
    //</editor-fold>
}
