//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:39:07 AM UTC 
//


package net.sourceforge.czt.circuspatt.jaxb.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import net.sourceforge.czt.circus.jaxb.gen.CircusNameSet;
import net.sourceforge.czt.circus.jaxb.gen.NameSet;
import net.sourceforge.czt.zpatt.jaxb.gen.Binding;


/**
 * <p>Java class for JokerNameSetBinding complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JokerNameSetBinding">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/zpatt}Binding">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/circuspatt}JokerNameSet"/>
 *         &lt;element ref="{http://czt.sourceforge.net/circus}NameSet"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JokerNameSetBinding", propOrder = {
    "jokerNameSet",
    "nameSet"
})
public class JokerNameSetBinding
    extends Binding
{

    @XmlElementRef(name = "JokerNameSet", namespace = "http://czt.sourceforge.net/circuspatt", type = JAXBElement.class)
    protected JAXBElement<JokerNameSet> jokerNameSet;
    @XmlElementRef(name = "NameSet", namespace = "http://czt.sourceforge.net/circus", type = JAXBElement.class)
    protected JAXBElement<? extends NameSet> nameSet;

    /**
     * Gets the value of the jokerNameSet property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JokerNameSet }{@code >}
     *     
     */
    public JAXBElement<JokerNameSet> getJokerNameSet() {
        return jokerNameSet;
    }

    /**
     * Sets the value of the jokerNameSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JokerNameSet }{@code >}
     *     
     */
    public void setJokerNameSet(JAXBElement<JokerNameSet> value) {
        this.jokerNameSet = value;
    }

    /**
     * Gets the value of the nameSet property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JokerNameSet }{@code >}
     *     {@link JAXBElement }{@code <}{@link CircusNameSet }{@code >}
     *     {@link JAXBElement }{@code <}{@link NameSet }{@code >}
     *     
     */
    public JAXBElement<? extends NameSet> getNameSet() {
        return nameSet;
    }

    /**
     * Sets the value of the nameSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JokerNameSet }{@code >}
     *     {@link JAXBElement }{@code <}{@link CircusNameSet }{@code >}
     *     {@link JAXBElement }{@code <}{@link NameSet }{@code >}
     *     
     */
    public void setNameSet(JAXBElement<? extends NameSet> value) {
        this.nameSet = value;
    }

}
