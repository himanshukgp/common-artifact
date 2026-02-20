package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "mentor-usage")
public class MentorUsage {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<userId>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // DATE#YYYY-MM-DD

    @DynamoDBAttribute(attributeName = "count")
    private Integer count;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk(String date) {
        return "DATE#" + date;
    }
}
