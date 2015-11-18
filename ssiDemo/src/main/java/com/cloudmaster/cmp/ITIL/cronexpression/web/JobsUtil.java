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

package com.cloudmaster.cmp.ITIL.cronexpression.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import com.cloudmaster.cmp.ITIL.cronexpression.dao.Jobs;

public class JobsUtil {

    protected static Map<String, List<Jobs.Job>> jobListMap;

    protected static void init(){
        try {
            if(jobListMap == null){
                jobListMap = new HashMap<String, List<Jobs.Job>>();
            }
            String fileName = new JobsUtil().getClass().getResource("").getFile();
            System.out.println(fileName);
            JAXBContext context = JAXBContext.newInstance(Jobs.class);
            Jobs jobs = (Jobs) context.createUnmarshaller().unmarshal(
                    new File(fileName + "Jobs.xml"));
            if (jobs.getJob().size() > 0) {
                for (Jobs.Job job : jobs.getJob()) {
                    String ssID = job.getSSID();
                    if (jobListMap.get(ssID) == null) {
                        List<Jobs.Job> jobList = new ArrayList<Jobs.Job>();
                        jobList.add(job);
                        jobListMap.put(ssID, jobList);
                    } else {
                        jobListMap.get(ssID).add(job);
                    }
                }
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
