//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:38:59 AM UTC 
//


package net.sourceforge.czt.circus.jaxb.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StateUpdateAnn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StateUpdateAnn">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/circus}CircusAnn">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/circus}StateUpdate"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateUpdateAnn", propOrder = {
    "stateUpdate"
})
public class StateUpdateAnn
    extends CircusAnn
{

    @XmlElementRef(name = "StateUpdate", namespace = "http://czt.sourceforge.net/circus", type = JAXBElement.class)
    protected JAXBElement<StateUpdate> stateUpdate;

    /**
     * Gets the value of the stateUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link StateUpdate }{@code >}
     *     
     */
    public JAXBElement<StateUpdate> getStateUpdate() {
        return stateUpdate;
    }

    /**
     * Sets the value of the stateUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link StateUpdate }{@code >}
     *     
     */
    public void setStateUpdate(JAXBElement<StateUpdate> value) {
        this.stateUpdate = value;
    }

}
