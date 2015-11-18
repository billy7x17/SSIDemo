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

/**
 * 
 * @author <a href="mailto:wengcz@neusoft.com"> wengcz </a>
 * @version 1.0.0 18 Mar 2012
 */
public enum ConstantEnum {
    SUCCESS("SUCCESS"), INVALID("INVALID"), INPUT("INPUT");

    private String value;

    /**
     * @deprecated
     */
    public static final int INT_1024 = 1024;

    /**
     * @deprecated
     */

    ConstantEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public String getName() {
        return "my name :" + value;
    }
}
