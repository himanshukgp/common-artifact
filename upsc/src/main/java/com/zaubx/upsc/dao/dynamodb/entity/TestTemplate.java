package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "upsc_test_templates")
public class TestTemplate {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<userId>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // TEMPLATE#<templateId>

    @DynamoDBAttribute(attributeName = "templateId")
    private String templateId;

    @DynamoDBAttribute(attributeName = "templateName")
    private String templateName;

    @DynamoDBAttribute(attributeName = "subjects")
    private List<String> subjects;

    @DynamoDBAttribute(attributeName = "types")
    private List<String> types;

    @DynamoDBAttribute(attributeName = "includePyq")
    private Boolean includePyq;

    @DynamoDBAttribute(attributeName = "pyqYears")
    private List<String> pyqYears;

    @DynamoDBAttribute(attributeName = "includeCurrentAffairs")
    private Boolean includeCurrentAffairs;

    @DynamoDBAttribute(attributeName = "questionLimit")
    private Integer questionLimit;

    @DynamoDBAttribute(attributeName = "createdAt")
    private Long createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private Long updatedAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk(String templateId) {
        return "TEMPLATE#" + templateId;
    }

    public static String generateTemplateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
