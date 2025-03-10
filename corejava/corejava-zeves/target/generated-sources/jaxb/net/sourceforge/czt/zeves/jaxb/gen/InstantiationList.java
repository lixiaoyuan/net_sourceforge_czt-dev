//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:38:17 AM UTC 
//


package net.sourceforge.czt.zeves.jaxb.gen;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import net.sourceforge.czt.z.jaxb.gen.RenameList;


/**
 * <p>Java class for InstantiationList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InstantiationList">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/zml}RenameList">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/zeves}Instantiation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstantiationList", propOrder = {
    "instantiation"
})
public class InstantiationList
    extends RenameList
{

    @XmlElementRef(name = "Instantiation", namespace = "http://czt.sourceforge.net/zeves", type = JAXBElement.class)
    protected List<JAXBElement<Instantiation>> instantiation;

    /**
     * Gets the value of the instantiation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instantiation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstantiation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Instantiation }{@code >}
     * 
     * 
     */
    public List<JAXBElement<Instantiation>> getInstantiation() {
        if (instantiation == null) {
            instantiation = new ArrayList<JAXBElement<Instantiation>>();
        }
        return this.instantiation;
    }

}
