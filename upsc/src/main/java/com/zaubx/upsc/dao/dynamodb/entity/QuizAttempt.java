package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.util.Set;

@Data
@DynamoDBTable(tableName = "upsc_quiz_attempts")
public class QuizAttempt {

    public static final String HASH_KEY = "pk";
    public static final String RANGE_KEY = "sk";

    // ---------- KEYS ----------

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<userId>

    @DynamoDBRangeKey(attributeName = RANGE_KEY)
    private String sk; // ATTEMPT#<attemptedAt>#Q#<questionId>

    // ---------- CORE ----------

    @DynamoDBAttribute(attributeName = "userId")
    private String userId;

    @DynamoDBAttribute(attributeName = "questionId")
    private String questionId;

    @DynamoDBAttribute(attributeName = "attemptedAt")
    private Long attemptedAt;

    @DynamoDBAttribute(attributeName = "timeTakenMs")
    private Long timeTakenMs;

    @DynamoDBAttribute(attributeName = "selectedOption")
    private String selectedOption;

    @DynamoDBAttribute(attributeName = "correct")
    private Boolean correct;

    // ---------- ANALYTICS DIMENSIONS ----------

    @DynamoDBAttribute(attributeName = "subjectId")
    private String subjectId;

    @DynamoDBAttribute(attributeName = "topicIds")
    private Set<String> topicIds;

    @DynamoDBAttribute(attributeName = "subtopicIds")
    private Set<String> subtopicIds;

    @DynamoDBAttribute(attributeName = "difficulty")
    private String difficulty;

    @DynamoDBAttribute(attributeName = "context")
    private String context; // CA | STATIC | PYQ | BUDGET

    // ---------- TIME BUCKETING ----------

    @DynamoDBAttribute(attributeName = "year")
    private Integer year;

    @DynamoDBAttribute(attributeName = "month")
    private Integer month;

    @DynamoDBAttribute(attributeName = "day")
    private Integer day;

    // ---------- OPTIONAL SESSION ----------

    @DynamoDBAttribute(attributeName = "sessionId")
    private String sessionId;

    // ---------- BUILDERS ----------

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk(long attemptedAt, String questionId) {
        return "ATTEMPT#" + attemptedAt + "#Q#" + questionId;
    }
}
