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
import net.sourceforge.czt.z.jaxb.gen.DeclList;
import net.sourceforge.czt.z.jaxb.gen.ExprList;
import net.sourceforge.czt.z.jaxb.gen.ZDeclList;
import net.sourceforge.czt.z.jaxb.gen.ZExprList;
import net.sourceforge.czt.zpatt.jaxb.gen.HeadDeclList;
import net.sourceforge.czt.zpatt.jaxb.gen.JokerDeclList;
import net.sourceforge.czt.zpatt.jaxb.gen.JokerExprList;


/**
 * <p>Java class for LetVarAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LetVarAction">
 *   &lt;complexContent>
 *     &lt;extension base="{http://czt.sourceforge.net/circus}LetAction">
 *       &lt;sequence>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}DeclList"/>
 *         &lt;element ref="{http://czt.sourceforge.net/zml}ExprList"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LetVarAction", propOrder = {
    "declList",
    "exprList"
})
public class LetVarAction
    extends LetAction
{

    @XmlElementRef(name = "DeclList", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<? extends DeclList> declList;
    @XmlElementRef(name = "ExprList", namespace = "http://czt.sourceforge.net/zml", type = JAXBElement.class)
    protected JAXBElement<? extends ExprList> exprList;

    /**
     * Gets the value of the declList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JokerDeclList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZDeclList }{@code >}
     *     {@link JAXBElement }{@code <}{@link HeadDeclList }{@code >}
     *     {@link JAXBElement }{@code <}{@link DeclList }{@code >}
     *     
     */
    public JAXBElement<? extends DeclList> getDeclList() {
        return declList;
    }

    /**
     * Sets the value of the declList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JokerDeclList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZDeclList }{@code >}
     *     {@link JAXBElement }{@code <}{@link HeadDeclList }{@code >}
     *     {@link JAXBElement }{@code <}{@link DeclList }{@code >}
     *     
     */
    public void setDeclList(JAXBElement<? extends DeclList> value) {
        this.declList = value;
    }

    /**
     * Gets the value of the exprList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link JokerExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZExprList }{@code >}
     *     
     */
    public JAXBElement<? extends ExprList> getExprList() {
        return exprList;
    }

    /**
     * Sets the value of the exprList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link JokerExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ExprList }{@code >}
     *     {@link JAXBElement }{@code <}{@link ZExprList }{@code >}
     *     
     */
    public void setExprList(JAXBElement<? extends ExprList> value) {
        this.exprList = value;
    }

}
