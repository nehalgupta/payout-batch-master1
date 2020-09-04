package com.samscash.batch.payoutbatchmaster.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payout {

    private String payoutDate;

    private String memUUID;

    private String payoutID;

    private String batchID;

    private String status;

    /*
    -  Payout_date - partitionkey
	-  MEMBERSHIP_UUID_Rewards_type -ID
	-  Payout_ID (correlationId) -indexed
	-  Batch_Id - YYYYMMDD_1
	-  Status
     */
}
