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
package com.cloudmaster.cmp.ITIL.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;

import com.neusoft.mid.enzyme.quzrtz.BaseJob;

/** 
 * 此类无特殊说明
 * @version 1.0.0 18 Mar 2012
 * @author mid<mid@neusoft.com>
 */
public class SpringJobTest extends BaseJob {

	@Override
	public void invoke(JobExecutionContext arg0) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d  H:m:s E");
        String s = format.format(new Date());
        System.out.println("################################Now is " + s);
	}

}
