package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.util.Map;

@Data
@DynamoDBTable(tableName = "mentor-snapshots")
public class MentorSnapshot {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<userId>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // SNAPSHOT#LATEST

    @DynamoDBAttribute(attributeName = "snapshot")
    @DynamoDBTypeConvertedJson
    private Map<String, Object> snapshot;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private Long updatedAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk() {
        return "SNAPSHOT#LATEST";
    }
}
