package com.samscash.batch.payoutbatchmaster.job.payout;

import com.samscash.batch.payoutbatchmaster.constants.BatchConstants;
import com.samscash.batch.payoutbatchmaster.job.BaseJobConfig;
import com.samscash.batch.payoutbatchmaster.job.JobCompletionNotificationListener;
import com.samscash.batch.payoutbatchmaster.mapper.PayoutBatchDbRowMapper;
import com.samscash.batch.payoutbatchmaster.model.Payout;
import com.samscash.batch.payoutbatchmaster.processor.PayoutBatchFileProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class PayoutBatchFIleJob extends BaseJobConfig {

    @Autowired
    private PayoutBatchFileProcessor payoutBatchFileProcessor;

    @Autowired
    private PayoutWriter payoutWriter;


    private DataSource dataSource;

    private static final int CHUNK_SIZE = 10;

    @Bean("payout")
    public Job job(JobCompletionNotificationListener listener) throws Exception {

        return jobBuilderFactory.get(BatchConstants.PAYOUT_JOB)
                .incrementer(new RunIdIncrementer()).listener(listener).start(getStep())
                .build();
    }

    public Step getStep() throws Exception {

        return stepBuilderFactory.get(BatchConstants.BAJFILE_STEP)
                .<Payout, Object>chunk(CHUNK_SIZE).reader(payoutDBReader()).processor(payoutBatchFileProcessor)
                .writer(payoutWriter).build();
    }

    @Bean
    public ItemStreamReader<Payout> payoutDBReader() {
        JdbcCursorItemReader<Payout> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("Select * from Payout_Batch where Payout_date = CURRENT_DATE");
        reader.setRowMapper(new PayoutBatchDbRowMapper());
        return reader;
    }

}
