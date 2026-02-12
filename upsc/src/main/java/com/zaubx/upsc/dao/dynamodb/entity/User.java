package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

@Data
@DynamoDBTable(tableName = "upsc_users")
public class User {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<sub>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // PROFILE

    @DynamoDBAttribute(attributeName = "userId")
    private String userId; // <sub>

    @DynamoDBAttribute(attributeName = "email")
    private String email;

    @DynamoDBAttribute(attributeName = "phone")
    private String phone;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "provider")
    private String provider; // COGNITO

    @DynamoDBAttribute(attributeName = "role")
    private String role; // USER | ADMIN

    @DynamoDBAttribute(attributeName = "status")
    private String status; // ACTIVE | BLOCKED

    @DynamoDBAttribute(attributeName = "createdAt")
    private Long createdAt;

    @DynamoDBAttribute(attributeName = "lastSeenAt")
    private Long lastSeenAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk() {
        return "PROFILE";
    }
}
