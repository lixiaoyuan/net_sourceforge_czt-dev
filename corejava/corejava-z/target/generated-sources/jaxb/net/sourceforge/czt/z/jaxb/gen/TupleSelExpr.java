//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:37:58 AM UTC 
//


package net.sourceforge.czt.z.jaxb.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TupleSelExpr complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TupleSelExpr">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/zml}Expr1">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}Numeral"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TupleSelExpr", propOrder = {
    "numeral"
})
public class TupleSelExpr
    extends Expr1
{

    @XmlElementRef(name = "Numeral", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<? extends Numeral> numeral;

    /**
     * Gets the value of the numeral property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Numeral }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZNumeral }{@code >}
     *     
     */
    public JAXBElement<? extends Numeral> getNumeral() {
        return numeral;
    }

    /**
     * Sets the value of the numeral property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Numeral }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZNumeral }{@code >}
     *     
     */
    public void setNumeral(JAXBElement<? extends Numeral> value) {
        this.numeral = value;
    }

}
