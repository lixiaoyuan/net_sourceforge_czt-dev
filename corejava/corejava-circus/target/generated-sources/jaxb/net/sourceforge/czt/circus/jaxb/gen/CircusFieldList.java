//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:38:59 AM UTC 
//


package net.sourceforge.czt.circus.jaxb.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CircusFieldList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CircusFieldList">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/circus}FieldList">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/circus}Field" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CircusFieldList", propOrder = {
    "field"
})
public class CircusFieldList
    extends FieldList
{

    @XmlElementRef(name = "Field", namespace = "http://czt.sourceforge.net/circus", type = JAXBElement.class)
    protected List<JAXBElement<? extends Field>> field;

    /**
     * Gets the value of the field property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the field property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Field }{@code >}
     * {@link JAXBElement }{@code <}{@link InputField }{@code >}
     * {@link JAXBElement }{@code <}{@link DotField }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends Field>> getField() {
        if (field == null) {
            field = new ArrayList<JAXBElement<? extends Field>>();
        }
        return this.field;
    }

}
