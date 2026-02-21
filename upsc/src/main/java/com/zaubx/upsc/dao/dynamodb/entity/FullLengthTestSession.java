package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "upsc_full_length_tests")
public class FullLengthTestSession {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<userId>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // SESSION#<sessionId>

    @DynamoDBAttribute(attributeName = "sessionId")
    private String sessionId;

    @DynamoDBAttribute(attributeName = "testName")
    private String testName; // e.g. "UPSC Prelims 2025 - Mock 1"

    @DynamoDBAttribute(attributeName = "testType")
    private String testType; // GS_PAPER_1 | GS_PAPER_2_CSAT

    @DynamoDBAttribute(attributeName = "totalQuestions")
    private Integer totalQuestions; // 100 for GS1, 80 for CSAT

    @DynamoDBAttribute(attributeName = "durationMinutes")
    private Integer durationMinutes; // 120

    @DynamoDBAttribute(attributeName = "status")
    private String status; // NOT_STARTED | IN_PROGRESS | COMPLETED | ABANDONED

    @DynamoDBAttribute(attributeName = "score")
    private Double score;

    @DynamoDBAttribute(attributeName = "correct")
    private Integer correct;

    @DynamoDBAttribute(attributeName = "wrong")
    private Integer wrong;

    @DynamoDBAttribute(attributeName = "unattempted")
    private Integer unattempted;

    @DynamoDBAttribute(attributeName = "negativeMarks")
    private Double negativeMarks;

    @DynamoDBAttribute(attributeName = "questionIds")
    private List<String> questionIds;

    @DynamoDBAttribute(attributeName = "startedAt")
    private Long startedAt;

    @DynamoDBAttribute(attributeName = "completedAt")
    private Long completedAt;

    @DynamoDBAttribute(attributeName = "createdAt")
    private Long createdAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk(String sessionId) {
        return "SESSION#" + sessionId;
    }

    public static String generateSessionId() {
        return UUID.randomUUID().toString().substring(0, 12);
    }
}
