package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "question-hashes")
public class QuestionHash {

    public static final String HASH_KEY = "questionHash";

    @DynamoDBHashKey(attributeName = "questionHash")
    private String questionHash;

    @DynamoDBAttribute(attributeName = "draftId")
    private String draftId;

    @DynamoDBAttribute(attributeName = "questionId")
    private String questionId;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;
}