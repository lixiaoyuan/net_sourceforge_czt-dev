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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AxPara complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AxPara">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/zml}Para">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}NameList"/>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}SchText"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Box" type="{http://czt.sourceforge.net/zml}Box" default="AxBox" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AxPara", propOrder = {
    "nameList",
    "schText"
})
public class AxPara
    extends Para
{

    @XmlElementRef(name = "NameList", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<? extends NameList> nameList;
    @XmlElementRef(name = "SchText", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<? extends SchText> schText;
    @XmlAttribute(name = "Box")
    protected Box box;

    /**
     * Gets the value of the nameList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ZNameList }{@code >}
     *     {@link JAXBElement }{@code <}{@link NameList }{@code >}
     *     
     */
    public JAXBElement<? extends NameList> getNameList() {
        return nameList;
    }

    /**
     * Sets the value of the nameList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ZNameList }{@code >}
     *     {@link JAXBElement }{@code <}{@link NameList }{@code >}
     *     
     */
    public void setNameList(JAXBElement<? extends NameList> value) {
        this.nameList = value;
    }

    /**
     * Gets the value of the schText property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link SchText }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZSchText }{@code >}
     *     
     */
    public JAXBElement<? extends SchText> getSchText() {
        return schText;
    }

    /**
     * Sets the value of the schText property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link SchText }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZSchText }{@code >}
     *     
     */
    public void setSchText(JAXBElement<? extends SchText> value) {
        this.schText = value;
    }

    /**
     * Gets the value of the box property.
     * 
     * @return
     *     possible object is
     *     {@link Box }
     *     
     */
    public Box getBox() {
        if (box == null) {
            return Box.AX_BOX;
        } else {
            return box;
        }
    }

    /**
     * Sets the value of the box property.
     * 
     * @param value
     *     allowed object is
     *     {@link Box }
     *     
     */
    public void setBox(Box value) {
        this.box = value;
    }

}
