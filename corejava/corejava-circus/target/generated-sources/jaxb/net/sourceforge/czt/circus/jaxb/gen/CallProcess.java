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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import net.sourceforge.czt.z.jaxb.gen.ExprList;
import net.sourceforge.czt.z.jaxb.gen.RefExpr;
import net.sourceforge.czt.z.jaxb.gen.ZExprList;
import net.sourceforge.czt.zpatt.jaxb.gen.JokerExprList;


/**
 * <p>Java class for CallProcess complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CallProcess">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/circus}CircusProcess">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}RefExpr"/>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}ExprList"/>
 *       &lt;/sequence>
 *       &lt;attribute name="CallUsage" type="{http://czt.sourceforge.net/circus}CallUsage" default="Parameterised" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CallProcess", propOrder = {
    "callExpr",
    "actuals"
})
public class CallProcess
    extends CircusProcess
{

    @XmlElementRef(name = "RefExpr", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<RefExpr> callExpr;
    @XmlElementRef(name = "ExprList", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<? extends ExprList> actuals;
    @XmlAttribute(name = "CallUsage")
    protected CallUsage callUsage;

    /**
     * Gets the value of the callExpr property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RefExpr }{@code >}
     *     
     */
    public JAXBElement<RefExpr> getCallExpr() {
        return callExpr;
    }

    /**
     * Sets the value of the callExpr property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RefExpr }{@code >}
     *     
     */
    public void setCallExpr(JAXBElement<RefExpr> value) {
        this.callExpr = value;
    }

    /**
     * Gets the value of the actuals property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JokerExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZExprList }{@code >}
     *     
     */
    public JAXBElement<? extends ExprList> getActuals() {
        return actuals;
    }

    /**
     * Sets the value of the actuals property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JokerExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZExprList }{@code >}
     *     
     */
    public void setActuals(JAXBElement<? extends ExprList> value) {
        this.actuals = value;
    }

    /**
     * Gets the value of the callUsage property.
     * 
     * @return
     *     possible object is
     *     {@link CallUsage }
     *     
     */
    public CallUsage getCallUsage() {
        if (callUsage == null) {
            return CallUsage.PARAMETERISED;
        } else {
            return callUsage;
        }
    }

    /**
     * Sets the value of the callUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link CallUsage }
     *     
     */
    public void setCallUsage(CallUsage value) {
        this.callUsage = value;
    }

}
