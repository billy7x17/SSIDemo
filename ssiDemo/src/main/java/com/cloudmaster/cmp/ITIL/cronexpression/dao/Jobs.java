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

package com.cloudmaster.cmp.ITIL.cronexpression.dao;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded">
 *         &lt;element name="job">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="SSID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="jobName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="beanID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/all>
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
    "job"
})
@XmlRootElement(name = "jobs")
public class Jobs {

    @XmlElement(required = true)
    protected List<Jobs.Job> job;

    /**
     * Gets the value of the job property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the job property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJob().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jobs.Job }
     *
     *
     */
    public List<Jobs.Job> getJob() {
        if (job == null) {
            job = new ArrayList<Jobs.Job>();
        }
        return this.job;
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
     *       &lt;all>
     *         &lt;element name="SSID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="jobName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="beanID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class Job {

        @XmlElement(name = "SSID", required = true)
        protected String ssid;
        @XmlElement(required = true)
        protected String jobName;
        @XmlElement(required = true)
        protected String beanID;

        /**
         * Gets the value of the ssid property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getSSID() {
            return ssid;
        }

        /**
         * Sets the value of the ssid property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setSSID(String value) {
            this.ssid = value;
        }

        /**
         * Gets the value of the jobName property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getJobName() {
            return jobName;
        }

        /**
         * Sets the value of the jobName property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setJobName(String value) {
            this.jobName = value;
        }

        /**
         * Gets the value of the beanID property.
         *
         * @return
         *     possible object is
         *     {@link String }
         *
         */
        public String getBeanID() {
            return beanID;
        }

        /**
         * Sets the value of the beanID property.
         *
         * @param value
         *     allowed object is
         *     {@link String }
         *
         */
        public void setBeanID(String value) {
            this.beanID = value;
        }

    }

}
