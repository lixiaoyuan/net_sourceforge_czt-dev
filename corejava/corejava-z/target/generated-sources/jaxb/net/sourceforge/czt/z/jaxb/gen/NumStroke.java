//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:37:58 AM UTC 
//


package net.sourceforge.czt.z.jaxb.gen;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import net.sourceforge.czt.base.ast.Digit;


/**
 * <p>Java class for NumStroke complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NumStroke">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/zml}Stroke">
 *       &lt;attribute name="Digit" use="required" type="{http://czt.sourceforge.net/zml}Digit" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumStroke")
public class NumStroke
    extends Stroke
{

    @XmlAttribute(name = "Digit", required = true)
    @XmlJavaTypeAdapter(Adapter1 .class)
    protected Digit digit;

    /**
     * Gets the value of the digit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Digit getDigit() {
        return digit;
    }

    /**
     * Sets the value of the digit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDigit(Digit value) {
        this.digit = value;
    }

}
