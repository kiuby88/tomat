package org.tomat.agnostic.elements;

import org.opentosca.model.tosca.TCapability;
import org.opentosca.model.tosca.TNodeTemplate;
import org.opentosca.model.tosca.TRequirement;
import org.opentosca.model.tosca.utils.DefinitionUtils;
import org.tomat.agnostic.Agnostic;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by MariaC on 24/09/2014.
 */
public class AgnosticElement implements Agnostic {

    private static final String DEFAULT_LOCATION = "localhost";

    private String id;
    private String name;
    private String type;
    private String location;
    private TNodeTemplate sourceNodeTemplate;
    private List<String> capabilitiesIds;
    private List<String> requirementsIds;

    public AgnosticElement() {
    }

    public AgnosticElement(String id) {
        this.id = id;
    }

    public AgnosticElement(TNodeTemplate nodeTemplate) {
        sourceNodeTemplate = nodeTemplate;
        initGenericValues();
        initCapabilitiesIds();
        initRequirementsIds();
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
        List<TCapability> capabilitiesOfSourceNodeTemplate = null;
        if ((sourceNodeTemplate != null) &&
                (sourceNodeTemplate.getCapabilities() != null)) {
            capabilitiesIds = new LinkedList<String>();
            capabilitiesOfSourceNodeTemplate = sourceNodeTemplate.getCapabilities().getCapability();
            for (TCapability capability : capabilitiesOfSourceNodeTemplate) {
                capabilitiesIds.add(capability.getId());
            }
        }
    }

    private void initRequirementsIds() {
        List<TRequirement> requirementsOfSourceNodeTemplate = null;
        if ((sourceNodeTemplate != null) &&
                (sourceNodeTemplate.getRequirements() != null)) {
            requirementsIds = new LinkedList<String>();
            requirementsOfSourceNodeTemplate = sourceNodeTemplate.getRequirements().getRequirement();
            for (TRequirement requirement : requirementsOfSourceNodeTemplate) {
                requirementsIds.add(requirement.getId());
            }
        }
    }

    Map<String, String> getNodeTemplateProperties() {
        return DefinitionUtils.getProperties(this.getNodeTemplate());

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
        if ((location == null) || (location.equals("")))
            this.location = DEFAULT_LOCATION;
        else
            this.location = location;
    }
    //</editor-fold>

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof AgnosticElement) {
            AgnosticElement that = (AgnosticElement) o;
            result = this.getId().equalsIgnoreCase(that.getId());
        }
        return result;
    }
}
