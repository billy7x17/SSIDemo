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
package com.cloudmaster.cmp.web;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Authorization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Authorization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDCAccessId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IDCPwd" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TransactionID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZoneID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 * @author mid<mid@neusoft.com>
 * @version 1.0.0 18 Mar 2012
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Authorization", propOrder = {
    "idcAccessId",
    "idcPwd",
    "timestamp",
    "transactionID",
    "zoneID"
})

@XmlRootElement(name = "Authorization")
public class Authorization {

    @XmlElement(name = "IDCAccessId", required = true)
    protected String idcAccessId;
    @XmlElement(name = "IDCPwd", required = true)
    protected String idcPwd;
    @XmlElement(name = "Timestamp", required = true)
    protected String timestamp;
    @XmlElement(name = "TransactionID", required = true)
    protected String transactionID;
    @XmlElement(name = "ZoneID")
    protected String zoneID;

    /**
     * Gets the value of the idcAccessId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCAccessId() {
        return idcAccessId;
    }

    /**
     * Sets the value of the idcAccessId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCAccessId(String value) {
        this.idcAccessId = value.trim();
    }

    /**
     * Gets the value of the idcPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCPwd() {
        return idcPwd;
    }

    /**
     * Sets the value of the idcPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCPwd(String value) {
        this.idcPwd = value.trim();
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value.trim();
    }

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value.trim();
    }

    /**
     * Gets the value of the zoneID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneID() {
        return zoneID;
    }

    /**
     * Sets the value of the zoneID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneID(String value) {
        this.zoneID = value.trim();
    }

}
