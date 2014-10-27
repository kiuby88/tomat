package org.tomat.agnostic.components;

import org.opentosca.model.tosca.TCapability;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TRequirement;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.AgnosticElement;
import org.tomat.agnostic.artifact.AgnosticDeploymentArtifact;
import org.tomat.agnostic.properties.AgnosticProperty;
import org.tomat.exceptions.AgnosticPropertyException;
import org.tomat.translate.TechnologyComponent;
import org.tomat.translate.TechnologyComponentFactory;
import org.tomat.translate.brooklyn.exceptions.AgnosticComponentTypeNotSupportedbyBrooklyException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Created by MariaC on 24/09/2014.
 */
//TODO migrate the name to AgnosticComponent and not AgnosticComponent
public abstract class AgnosticComponent implements AgnosticElement {


    private static final String DEFAULT_LOCATION = "localhost";
    //private static final Class<?> ROOT_AGNOSTIC_PROPERTY_CLASS = AgnosticProperty.class;

    private String id;
    private String name;
    //private String type;
    private String location;
    private TNodeTemplate sourceNodeTemplate;
    private List<AgnosticProperty> properties;
    private List<String> capabilitiesIds;
    private List<String> requirementsIds;
    private List<AgnosticDeploymentArtifact> agnosticDeploymentArtifacts;

    public AgnosticComponent() {
        properties = new LinkedList<>();
        capabilitiesIds = new LinkedList<>();
        requirementsIds = new LinkedList<>();
        setAgnosticDeploymentArtifacts(new LinkedList<AgnosticDeploymentArtifact>());
    }

    public AgnosticComponent(String id) {
        this.id = id;
    }

    public AgnosticComponent(TNodeTemplate nodeTemplate) throws AgnosticPropertyException {
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
            //TODO delete the next line because the type is specify
            //TODO by the AgnosticComponentConcreted
            //type = DefinitionUtils.getTypeName(sourceNodeTemplate);
            setLocation(sourceNodeTemplate);
        }
    }

    private void setLocation(TNodeTemplate nodeTemplate) {
        if (locationIsEmpty(nodeTemplate)) {
            location = DEFAULT_LOCATION;
        } else {
            location = nodeTemplate.getLocation().getLocationId();
        }
    }

    private boolean locationIsEmpty(TNodeTemplate nodeTemplate) {
        return ((nodeTemplate.getLocation() == null)
                || (nodeTemplate.getLocation().getLocationId() == null)
                || (nodeTemplate.getLocation().getLocationId().equals("")));
    }

    //TODO could be interesting delete this component of the agnostic Components
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

    //TODO could be interesting delete this component of the agnostic Components
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

        AgnosticProperty agnosticProperty;
        Class expectedPropertySpecification;
        Map<String, Class<? extends AgnosticProperty>> expectedProperties = this.getExpectedProperties();
        Set<String> propertyIdsOfExpectedProperties = expectedProperties.keySet();
        setProperties(new LinkedList<AgnosticProperty>());

        for (String expectedPropertyId : propertyIdsOfExpectedProperties) {
            expectedPropertySpecification = expectedProperties.get(expectedPropertyId);
            agnosticProperty=
                    buildExpectedProperty(expectedPropertyId, expectedPropertySpecification);
            addPropertyIfIsCompleted(agnosticProperty);
        }
    }

    private AgnosticProperty buildExpectedProperty(String id, Class<? extends AgnosticProperty> propertyClass)
            throws AgnosticPropertyException {
        AgnosticProperty result = null;
        try {
            Map<String, String> nodeTemplatePropertyMap = getNodeTemplateProperties();
            if (nodeTemplatePropertyMap != null) {
                result = propertyClass
                        .getConstructor(Map.class)
                        .newInstance(nodeTemplatePropertyMap);
            }
        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            throw new AgnosticPropertyException("Problem  instantiation: " + propertyClass.getName()
                    + ". Original Exception " + e.toString());
        }
        return result;
    }

    private void addPropertyIfIsCompleted(AgnosticProperty property) {
        if ((property!=null)&&(property.isCompleted())) {
            addProperty(property);
        }
    }

    private void addProperty(AgnosticProperty property) {
        getProperties().add(property);
    }

    //TODO probar este metodo en package visibility to check that only the package classes are able to use it
    //TODO change the name to getAllowedProperties (NOT USING REFACTORING, changing manually).
    public Map<String, Class<? extends AgnosticProperty>> getExpectedProperties() {
        return new HashMap<>();
    }

    /*
    TODO este metodo es demasiado pesado, habría que cambiarlo para que no siempre se tenga que llamar al metodo que
    TODO construye el mapa en lowercase, el test tarda medio segundo x culpa de este metodo. cuando denria de tardar solo
    TODO medio segundo maximo
    TODO optimice
     */
    Map<String, String> getNodeTemplateProperties() {
        return AgnosticComponentUtils.putLowerCaseMapKeys(DefinitionUtils.getProperties(this.getNodeTemplate()));
    }

    //TODO esto está feisimo aquí, pero feo feo.
    public abstract TechnologyComponent buildTechnologyComponent(TechnologyComponentFactory factory) throws AgnosticComponentTypeNotSupportedbyBrooklyException;

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

    //TODO do it is component abstract to will be concreted in the subclass
    public abstract String getType();

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

    public List<AgnosticDeploymentArtifact> getAgnosticDeploymentArtifacts() {
        return agnosticDeploymentArtifacts;
    }

    public void setAgnosticDeploymentArtifacts(List<AgnosticDeploymentArtifact> agnosticDeploymentArtifacts) {
        this.agnosticDeploymentArtifacts = agnosticDeploymentArtifacts;
    }
    //</editor-fold>
}
