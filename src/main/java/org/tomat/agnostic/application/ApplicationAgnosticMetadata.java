package org.tomat.agnostic.application;

import org.opentosca.model.tosca.TServiceTemplate;
import org.tomat.agnostic.Agnostic;

/**
 * Created by Jose on 18/10/14.
 */
public class ApplicationAgnosticMetadata implements Agnostic {

    public final static String APPLICATION_NAME_BY_DEFAULT = "APPLICATION_NAME";
    public final static String APPLICATION_ID_BY_DEFAULT = "APPLICATION_ID";
    private String name;
    private String id;

    public ApplicationAgnosticMetadata() {
        setName(APPLICATION_NAME_BY_DEFAULT);
        setId(APPLICATION_ID_BY_DEFAULT);
    }

    public ApplicationAgnosticMetadata(TServiceTemplate serviceTemplate) {
        setName(serviceTemplate.getName());
        setId(serviceTemplate.getId());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
        else{
            this.name=APPLICATION_NAME_BY_DEFAULT;
        }
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        if (id != null) {
            this.id = id;
        }
        else{
            this.id=APPLICATION_ID_BY_DEFAULT;
        }
    }
}
