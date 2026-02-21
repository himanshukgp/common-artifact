package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.util.UUID;

@Data
@DynamoDBTable(tableName = "upsc_study_plan_tasks")
public class StudyPlanTask {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<userId>#PLAN#<planId>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // TASK#<dayNumber>#<taskId>

    @DynamoDBAttribute(attributeName = "taskId")
    private String taskId;

    @DynamoDBAttribute(attributeName = "planId")
    private String planId;

    @DynamoDBAttribute(attributeName = "dayNumber")
    private Integer dayNumber; // day 1, 2, 3... of the plan

    @DynamoDBAttribute(attributeName = "weekNumber")
    private Integer weekNumber;

    @DynamoDBAttribute(attributeName = "subject")
    private String subject;

    @DynamoDBAttribute(attributeName = "topic")
    private String topic;

    @DynamoDBAttribute(attributeName = "description")
    private String description;

    @DynamoDBAttribute(attributeName = "taskType")
    private String taskType; // STUDY | PRACTICE | REVISION | TEST

    @DynamoDBAttribute(attributeName = "durationMinutes")
    private Integer durationMinutes;

    @DynamoDBAttribute(attributeName = "status")
    private String status; // PENDING | IN_PROGRESS | COMPLETED | SKIPPED

    @DynamoDBAttribute(attributeName = "completedAt")
    private Long completedAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId, String planId) {
        return "USER#" + userId + "#PLAN#" + planId;
    }

    public static String buildSk(int dayNumber, String taskId) {
        return "TASK#" + String.format("%03d", dayNumber) + "#" + taskId;
    }

    public static String generateTaskId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
