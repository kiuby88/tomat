package org.opentosca.model.tosca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Jose on 04/10/14.
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "location")
public class TLocation {


    @XmlAttribute(name = "locationId")
    protected String locationId;


    public String getLocationId(){
        return locationId;
    }

    public void setLocation(String location){
        this.locationId=location;
    }


}
