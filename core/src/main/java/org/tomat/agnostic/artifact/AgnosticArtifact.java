package org.tomat.agnostic.artifact;

import org.opentosca.model.tosca.TArtifactReference;
import org.opentosca.model.tosca.TArtifactTemplate;
import org.tomat.agnostic.Agnostic;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jose on 20/10/14.
 */
public class AgnosticArtifact implements Agnostic {


    private String type;
    private List<String> artifactReferences;

    public AgnosticArtifact(TArtifactTemplate artifactTemplate){
        setType(artifactTemplate.getType().getLocalPart());
        initArtifactReferences(artifactTemplate);
    }

    private void initArtifactReferences(TArtifactTemplate artifactTemplate) {
        setArtifactReferences(new LinkedList<String>());
        List<TArtifactReference> references =
                artifactTemplate.getArtifactReferences().getArtifactReference();

        for(TArtifactReference reference: references){
            getArtifactReferences().add(reference.getReference());
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getArtifactReferences() {
        return artifactReferences;
    }

    public void setArtifactReferences(List<String> artifactReferences) {
        this.artifactReferences = artifactReferences;
    }
}
