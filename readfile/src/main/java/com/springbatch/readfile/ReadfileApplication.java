package com.springbatch.readfile;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ReadfileApplication {
	
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	public static void main(String[] args) {
		
		SpringApplication.run(ReadfileApplication.class, args);
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void perform() throws Exception {
		
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		
		jobLauncher.run(job, params);
	}

	/*
	 * @InitBinder public void initBinder(WebDataBinder binder, WebRequest request)
	 * { //convert the date Note that the conversion here should always be in the
	 * same format as the string passed in, e.g. 2015-9-9 should be yyyy-MM-dd
	 * DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,
	 * true));// CustomDateEditor is a custom date editor
	 */
}
