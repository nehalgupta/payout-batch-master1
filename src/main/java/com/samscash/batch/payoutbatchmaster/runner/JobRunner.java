package com.samscash.batch.payoutbatchmaster.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;

public class JobRunner {

    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);

    @Autowired
    private JobLauncher simpleJobLauncher;

    @Autowired
    @Qualifier("payout")
    private Job payoutJob;

    @Autowired
    public JobRunner(Job payoutJob, JobLauncher jobLauncher) {
        this.simpleJobLauncher = jobLauncher;
        this.payoutJob = payoutJob;
    }


    @Async
    public void runBatchJob(){
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("date", new Date(), true);
        runJob(payoutJob, jobParametersBuilder.toJobParameters());
    }


    public void runJob(Job payoutJob, JobParameters parameters) {
        try {
            JobExecution jobExecution = simpleJobLauncher.run(payoutJob, parameters);
        }
        catch (JobExecutionAlreadyRunningException e) {
            logger.info("Job is already running.");
        } catch (JobRestartException e) {
            logger.info("Job was not restarted.");
        } catch (JobInstanceAlreadyCompleteException e) {
            logger.info("Job already completed.");
        } catch (JobParametersInvalidException e) {
            logger.info("Invalid job parameters.");
        }
    }
}
