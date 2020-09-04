package com.samscash.batch.payoutbatchmaster.mapper;

import com.samscash.batch.payoutbatchmaster.model.Payout;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PayoutBatchDbRowMapper implements RowMapper<Payout> {


    @Override
    public Payout mapRow(ResultSet resultSet, int i) throws SQLException {
        Payout payout = new Payout();
        payout.setPayoutDate(resultSet.getString("employee_id"));
        payout.setPayoutID(resultSet.getString("first_name"));
        payout.setMemUUID(resultSet.getString("last_name"));
        payout.setStatus(resultSet.getString("email"));
        return payout;
    }
}
