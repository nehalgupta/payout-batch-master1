package com.samscash.batch.payoutbatchmaster.controller;

import com.samscash.batch.payoutbatchmaster.runner.JobRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/run")
    public class JobController {
        private JobRunner jobRunner;
        @Autowired
        public JobController(JobRunner jobRunner) {
            this.jobRunner = jobRunner;
        }

        @RequestMapping(value = "/payoutJob")
        public String runJob() {
            jobRunner.runBatchJob();
            return String.format("Payout submitted successfully.");
        }
    }
