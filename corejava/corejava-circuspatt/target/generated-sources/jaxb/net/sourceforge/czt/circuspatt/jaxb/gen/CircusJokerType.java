//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.10.18 at 08:39:07 AM UTC 
//


package net.sourceforge.czt.circuspatt.jaxb.gen;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CircusJokerType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="CircusJokerType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Process"/>
 *     &lt;enumeration value="Action"/>
 *     &lt;enumeration value="Communication"/>
 *     &lt;enumeration value="ChanelSet"/>
 *     &lt;enumeration value="NameSet"/>
 *     &lt;enumeration value="Para"/>
 *     &lt;enumeration value="ParaList"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "CircusJokerType")
@XmlEnum
public enum CircusJokerType {

    @XmlEnumValue("Process")
    PROCESS("Process"),
    @XmlEnumValue("Action")
    ACTION("Action"),
    @XmlEnumValue("Communication")
    COMMUNICATION("Communication"),
    @XmlEnumValue("ChanelSet")
    CHANEL_SET("ChanelSet"),
    @XmlEnumValue("NameSet")
    NAME_SET("NameSet"),
    @XmlEnumValue("Para")
    PARA("Para"),
    @XmlEnumValue("ParaList")
    PARA_LIST("ParaList");
    private final String value;

    CircusJokerType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CircusJokerType fromValue(String v) {
        for (CircusJokerType c: CircusJokerType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
