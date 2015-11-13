//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// Reference Implementation, v2.2.4-2
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source
// schema.
// Generated on: 2013.07.10 at 12:45:26 PM CEST
//
// TOSCA version: TOSCA-v1.0-cs02.xsd
//

package org.opentosca.model.tosca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for tArtifactReference complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="tArtifactReference">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Include">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="pattern" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Exclude">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="pattern" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *       &lt;attribute name="reference" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tArtifactReference", propOrder = {"includeOrExclude"})
public class TArtifactReference {
	
	@XmlElements({@XmlElement(name = "Include", type = org.opentosca.model.tosca.TArtifactReference.Include.class), @XmlElement(name = "Exclude", type = org.opentosca.model.tosca.TArtifactReference.Exclude.class)})
	protected List<Object> includeOrExclude;
	@XmlAttribute(name = "reference", required = true)
	@XmlSchemaType(name = "anyURI")
	protected String reference;


	/**
	 * Gets the value of the includeOrExclude property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the includeOrExclude property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 *
	 * <pre>
	 * getIncludeOrExclude().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link org.opentosca.model.tosca.TArtifactReference.Include } {@link org.opentosca.model.tosca.TArtifactReference.Exclude }
	 * 
	 * 
	 */
	public List<Object> getIncludeOrExclude() {
		if (this.includeOrExclude == null) {
			this.includeOrExclude = new ArrayList<Object>();
		}
		return this.includeOrExclude;
	}
	
	/**
	 * Gets the value of the reference property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getReference() {
		return this.reference;
	}
	
	/**
	 * Sets the value of the reference property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setReference(String value) {
		this.reference = value;
	}
	
	
	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;attribute name="pattern" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Exclude {
		
		@XmlAttribute(name = "pattern", required = true)
		protected String pattern;
		
		
		/**
		 * Gets the value of the pattern property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getPattern() {
			return this.pattern;
		}
		
		/**
		 * Sets the value of the pattern property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setPattern(String value) {
			this.pattern = value;
		}
		
	}
	
	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;attribute name="pattern" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "")
	public static class Include {
		
		@XmlAttribute(name = "pattern", required = true)
		protected String pattern;
		
		
		/**
		 * Gets the value of the pattern property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getPattern() {
			return this.pattern;
		}
		
		/**
		 * Sets the value of the pattern property.
		 * 
		 * @param value allowed object is {@link String }
		 * 
		 */
		public void setPattern(String value) {
			this.pattern = value;
		}
		
	}
	
}
