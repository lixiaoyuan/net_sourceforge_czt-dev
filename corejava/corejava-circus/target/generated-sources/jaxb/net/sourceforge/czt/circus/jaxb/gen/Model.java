//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:38:59 AM UTC 
//


package net.sourceforge.czt.circus.jaxb.gen;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Model.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Model">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Tr"/>
 *     &lt;enumeration value="SFl"/>
 *     &lt;enumeration value="FlDv"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Model")
@XmlEnum
public enum Model {

    @XmlEnumValue("Tr")
    TR("Tr"),
    @XmlEnumValue("SFl")
    S_FL("SFl"),
    @XmlEnumValue("FlDv")
    FL_DV("FlDv");
    private final String value;

    Model(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Model fromValue(String v) {
        for (Model c: Model.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
