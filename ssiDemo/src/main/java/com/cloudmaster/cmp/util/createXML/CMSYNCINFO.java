/**
 * Copyright 2013 China Mobile Research Institute. All Right Reserved
 *
 * This file is owned by China Mobile and you may not use, modify, copy,
 * redistribute this file without written permissions.
 *
 * These Terms of Use define legal use of this file, all updates, revisions,
 * substitutions for you. All rights not expressly granted to you are reserved
 * by Chinamobile.
 */
package com.cloudmaster.cmp.util.createXML;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CONFIGURATION_INFO" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="instance_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ci_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element ref="{http://latest/rc-nms/CMFileData}CIATTRIBUTE_INFO" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;element name="creation_time" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="last_upate_time" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "configurationinfo"
})
@XmlRootElement(name = "CM_SYNC_INFO")
public class CMSYNCINFO {

    @XmlElement(name = "CONFIGURATION_INFO", required = true)
    protected List<CMSYNCINFO.CONFIGURATIONINFO> configurationinfo;

    /**
     * Gets the value of the configurationinfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the configurationinfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCONFIGURATIONINFO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CMSYNCINFO.CONFIGURATIONINFO }
     * 
     * 
     */
    public List<CMSYNCINFO.CONFIGURATIONINFO> getCONFIGURATIONINFO() {
        if (configurationinfo == null) {
            configurationinfo = new ArrayList<CMSYNCINFO.CONFIGURATIONINFO>();
        }
        return this.configurationinfo;
    }

    
    public void setCONFIGURATIONINFO(List<CMSYNCINFO.CONFIGURATIONINFO> list) {
        this.configurationinfo = list;
    }
    
    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="instance_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ci_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element ref="{http://latest/rc-nms/CMFileData}CIATTRIBUTE_INFO" maxOccurs="unbounded" minOccurs="0"/>
     *         &lt;element name="creation_time" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="last_upate_time" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "instanceId",
        "ciId",
        "ciattributeinfo",
        "creationTime",
        "lastUpateTime",
        "status"
    })
    public static class CONFIGURATIONINFO {

        @XmlElement(name = "instance_id", required = true)
        protected String instanceId;
        @XmlElement(name = "ci_id", required = true)
        protected String ciId;
        @XmlElement(name = "CIATTRIBUTE_INFO")
        protected List<CIATTRIBUTEINFO> ciattributeinfo;
        @XmlElement(name = "creation_time", required = true)
        @XmlSchemaType(name = "date")
        protected String creationTime;
        @XmlElement(name = "last_update_time", required = true)
        @XmlSchemaType(name = "date")
        protected String lastUpateTime;
        protected int status;

        /**
         * Gets the value of the instanceId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInstanceId() {
            return instanceId;
        }

        /**
         * Sets the value of the instanceId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInstanceId(String value) {
            this.instanceId = value;
        }

        /**
         * Gets the value of the ciId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCiId() {
            return ciId;
        }

        /**
         * Sets the value of the ciId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCiId(String value) {
            this.ciId = value;
        }

        /**
         * Gets the value of the ciattributeinfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ciattributeinfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCIATTRIBUTEINFO().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link CIATTRIBUTEINFO }
         * 
         * 
         */
        public List<CIATTRIBUTEINFO> getCIATTRIBUTEINFO() {
            if (ciattributeinfo == null) {
                ciattributeinfo = new ArrayList<CIATTRIBUTEINFO>();
            }
            return this.ciattributeinfo;
        }
        public void setCIATTRIBUTEINFO(List<CIATTRIBUTEINFO> list) {
            this.ciattributeinfo = list;
        }
        /**
         * Gets the value of the creationTime property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public String getCreationTime() {
            return creationTime;
        }

        /**
         * Sets the value of the creationTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCreationTime(String value) {
            this.creationTime = value;
        }

        /**
         * Gets the value of the lastUpateTime property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public String getLastUpateTime() {
            return lastUpateTime;
        }

        /**
         * Sets the value of the lastUpateTime property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setLastUpateTime(String value) {
            this.lastUpateTime = value;
        }

        /**
         * Gets the value of the status property.
         * 
         */
        public int getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         */
        public void setStatus(int value) {
            this.status = value;
        }

    }

}
