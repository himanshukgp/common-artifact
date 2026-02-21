package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "upsc_study_plans")
public class StudyPlan {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<userId>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // PLAN#<planId>

    @DynamoDBAttribute(attributeName = "planId")
    private String planId;

    @DynamoDBAttribute(attributeName = "planName")
    private String planName;

    @DynamoDBAttribute(attributeName = "isDefault")
    private Boolean isDefault;

    @DynamoDBAttribute(attributeName = "createdAt")
    private Long createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private Long updatedAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk(String planId) {
        return "PLAN#" + planId;
    }

    public static String generatePlanId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
