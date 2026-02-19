package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "knowledge-base")
public class KnowledgeBase {

    public static final String HASH_KEY = "topicName";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String topicName;

    @DynamoDBAttribute(attributeName = "topicContent")
    private String topicContent;
}
