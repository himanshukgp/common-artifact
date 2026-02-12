package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "upsc_user_question_attempts")
public class UserQuestionAttempt {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<sub>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // QUESTION#<questionId>#ATTEMPT#<epoch>

    @DynamoDBAttribute(attributeName = "questionId")
    private String questionId;

    @DynamoDBAttribute(attributeName = "subject")
    private String subject;

    @DynamoDBAttribute(attributeName = "topic")
    private String topic;

    @DynamoDBAttribute(attributeName = "subtopic")
    private String subtopic;

    @DynamoDBAttribute(attributeName = "type")
    private String type; // CA | STATIC | PYQ

    @DynamoDBAttribute(attributeName = "selectedOption")
    private String selectedOption; // A/B/C/D

    @DynamoDBAttribute(attributeName = "correct")
    private Boolean correct;

    @DynamoDBAttribute(attributeName = "answeredAt")
    private Long answeredAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk(String questionId, long epoch) {
        return "QUESTION#" + questionId + "#ATTEMPT#" + epoch;
    }
}
