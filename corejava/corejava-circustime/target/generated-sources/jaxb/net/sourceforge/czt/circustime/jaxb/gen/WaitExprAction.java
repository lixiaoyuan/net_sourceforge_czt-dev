//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:39:14 AM UTC 
//


package net.sourceforge.czt.circustime.jaxb.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import net.sourceforge.czt.z.jaxb.gen.Name;
import net.sourceforge.czt.z.jaxb.gen.ZName;
import net.sourceforge.czt.zpatt.jaxb.gen.JokerName;


/**
 * <p>Java class for WaitExprAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WaitExprAction">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/circustime}ActionTime1">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}Name"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WaitExprAction", propOrder = {
    "name"
})
public class WaitExprAction
    extends ActionTime1
{

    @XmlElementRef(name = "Name", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<? extends Name> name;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JokerName }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZName }{@code >}
     *     {@link JAXBElement }{@code <}{@link Name }{@code >}
     *     
     */
    public JAXBElement<? extends Name> getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JokerName }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZName }{@code >}
     *     {@link JAXBElement }{@code <}{@link Name }{@code >}
     *     
     */
    public void setName(JAXBElement<? extends Name> value) {
        this.name = value;
    }

}
