package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.StudyPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StudyPlanDao {

    private final DynamoDBMapper mapper;

    public void save(StudyPlan plan) {
        log.info("[STUDY_PLAN_DAO] save - planId={}", plan.getPlanId());
        mapper.save(plan);
    }

    public List<StudyPlan> findByUserId(String userId) {
        DynamoDBQueryExpression<StudyPlan> query = new DynamoDBQueryExpression<StudyPlan>()
                .withKeyConditionExpression("pk = :pk AND begins_with(sk, :prefix)")
                .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(StudyPlan.buildPk(userId)),
                        ":prefix", new AttributeValue().withS("PLAN#")
                ))
                .withScanIndexForward(false);

        return mapper.query(StudyPlan.class, query);
    }

    public Optional<StudyPlan> findByUserIdAndPlanId(String userId, String planId) {
        StudyPlan result = mapper.load(
                StudyPlan.class,
                StudyPlan.buildPk(userId),
                StudyPlan.buildSk(planId)
        );
        return Optional.ofNullable(result);
    }

    public void delete(String userId, String planId) {
        StudyPlan key = new StudyPlan();
        key.setPk(StudyPlan.buildPk(userId));
        key.setSk(StudyPlan.buildSk(planId));
        mapper.delete(key);
        log.info("[STUDY_PLAN_DAO] deleted - planId={}", planId);
    }
}
