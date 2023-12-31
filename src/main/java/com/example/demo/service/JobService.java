package com.example.demo.service;

import com.example.demo.mode.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
public class JobService {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
//    @Qualifier("firstJob")
    private Job firstJob;

    @Bean
    public String getTest() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/1", String.class);
    }

    //    @Autowired
//    @Qualifier("secondJob")
//    private Job secondJob;
    @Async
    public void startJob(String startJob) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        HashMap<String, JobParameter> params = new HashMap<>();
        params.put("first", new JobParameter("hasan11" + Math.random()));

        JobParameters jobParameters = new JobParameters(params);
        jobLauncher.run(firstJob, jobParameters);
    }
}
