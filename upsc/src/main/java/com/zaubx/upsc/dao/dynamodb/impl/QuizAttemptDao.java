package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.QuizAttempt;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class QuizAttemptDao {

    private final DynamoDBMapper mapper;

    public void save(QuizAttempt attempt) {
        mapper.save(attempt);
    }

    public List<QuizAttempt> findByUserId(String userId) {
        DynamoDBQueryExpression<QuizAttempt> query = new DynamoDBQueryExpression<QuizAttempt>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(QuizAttempt.buildPk(userId))))
                .withScanIndexForward(false);

        return mapper.query(QuizAttempt.class, query);
    }

    public List<QuizAttempt> findRecentByUserId(String userId, int limit) {
        DynamoDBQueryExpression<QuizAttempt> query = new DynamoDBQueryExpression<QuizAttempt>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(QuizAttempt.buildPk(userId))))
                .withScanIndexForward(false)
                .withLimit(limit);

        return mapper.query(QuizAttempt.class, query);
    }
}
