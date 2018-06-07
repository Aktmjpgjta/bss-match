package com.yingu.match.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.yingu.match.model.ScheduleJob;



/**
 * 
 * @Description:测试定时任务
 * @author:	yumingjun
 * @date:	2017-8-23 上午10:33:30
 */
@DisallowConcurrentExecution  
public class TaskA implements Job {
	 
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");    
        String taskName=scheduleJob.getName();
	    System.out.println("任务名称 = [" + taskName + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行"); 
    }
    
}
