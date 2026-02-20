package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.MentorUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MentorUsageDao {

    private final DynamoDBMapper mapper;

    public MentorUsage findByUserIdAndDate(String userId, String date) {
        try {
            String pk = MentorUsage.buildPk(userId);
            String sk = MentorUsage.buildSk(date);
            log.debug("[MENTOR_USAGE_DAO] findByUserIdAndDate - userId={}, date={}", userId, date);
            return mapper.load(MentorUsage.class, pk, sk);
        } catch (Exception e) {
            log.error("[MENTOR_USAGE_DAO] findByUserIdAndDate failed - userId={}, date={}", userId, date, e);
            throw e;
        }
    }

    public List<MentorUsage> findByUserId(String userId) {
        try {
            log.debug("[MENTOR_USAGE_DAO] findByUserId - userId={}", userId);
            DynamoDBQueryExpression<MentorUsage> query = new DynamoDBQueryExpression<MentorUsage>()
                    .withKeyConditionExpression("pk = :pk")
                    .withExpressionAttributeValues(Map.of(
                            ":pk", new AttributeValue().withS(MentorUsage.buildPk(userId))
                    ))
                    .withScanIndexForward(false);

            List<MentorUsage> results = mapper.query(MentorUsage.class, query);
            log.info("[MENTOR_USAGE_DAO] findByUserId completed - userId={}, resultCount={}", userId, results.size());
            return results;
        } catch (Exception e) {
            log.error("[MENTOR_USAGE_DAO] findByUserId failed - userId={}", userId, e);
            throw e;
        }
    }

    public void save(MentorUsage usage) {
        try {
            log.debug("[MENTOR_USAGE_DAO] save - pk={}, sk={}", usage.getPk(), usage.getSk());
            mapper.save(usage);
            log.info("[MENTOR_USAGE_DAO] save completed - pk={}, sk={}", usage.getPk(), usage.getSk());
        } catch (Exception e) {
            log.error("[MENTOR_USAGE_DAO] save failed - pk={}, sk={}", usage.getPk(), usage.getSk(), e);
            throw e;
        }
    }
}
