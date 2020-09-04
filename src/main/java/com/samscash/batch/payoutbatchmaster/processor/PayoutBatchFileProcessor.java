package com.samscash.batch.payoutbatchmaster.processor;

import com.samscash.batch.payoutbatchmaster.model.Payout;
import org.springframework.batch.item.ItemProcessor;

public class PayoutBatchFileProcessor implements ItemProcessor<Payout, Payout> {
    @Override
    public Payout process(Payout payout) throws Exception {
        return null;
    }
}
