package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.StudyPlanTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StudyPlanTaskDao {

    private final DynamoDBMapper mapper;

    public void save(StudyPlanTask task) {
        mapper.save(task);
    }

    public void saveBatch(List<StudyPlanTask> tasks) {
        log.info("[STUDY_PLAN_TASK_DAO] saveBatch - count={}", tasks.size());
        List<DynamoDBMapper.FailedBatch> failures = mapper.batchSave(tasks);
        if (!failures.isEmpty()) {
            log.error("[STUDY_PLAN_TASK_DAO] saveBatch had {} failures", failures.size());
        }
    }

    public List<StudyPlanTask> findByUserAndPlan(String userId, String planId) {
        DynamoDBQueryExpression<StudyPlanTask> query = new DynamoDBQueryExpression<StudyPlanTask>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(StudyPlanTask.buildPk(userId, planId))
                ))
                .withScanIndexForward(true);

        return mapper.query(StudyPlanTask.class, query);
    }

    public List<StudyPlanTask> findByUserPlanAndDay(String userId, String planId, int dayNumber) {
        String dayPrefix = "TASK#" + String.format("%03d", dayNumber) + "#";
        DynamoDBQueryExpression<StudyPlanTask> query = new DynamoDBQueryExpression<StudyPlanTask>()
                .withKeyConditionExpression("pk = :pk AND begins_with(sk, :skPrefix)")
                .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(StudyPlanTask.buildPk(userId, planId)),
                        ":skPrefix", new AttributeValue().withS(dayPrefix)
                ))
                .withScanIndexForward(true);

        return mapper.query(StudyPlanTask.class, query);
    }

    public Optional<StudyPlanTask> findTask(String userId, String planId, int dayNumber, String taskId) {
        StudyPlanTask result = mapper.load(
                StudyPlanTask.class,
                StudyPlanTask.buildPk(userId, planId),
                StudyPlanTask.buildSk(dayNumber, taskId)
        );
        return Optional.ofNullable(result);
    }

    public void deleteAllByUserAndPlan(String userId, String planId) {
        List<StudyPlanTask> tasks = findByUserAndPlan(userId, planId);
        if (!tasks.isEmpty()) {
            List<DynamoDBMapper.FailedBatch> failures = mapper.batchDelete(tasks);
            if (!failures.isEmpty()) {
                log.error("[STUDY_PLAN_TASK_DAO] deleteAll had {} failures", failures.size());
            }
        }
    }
}
