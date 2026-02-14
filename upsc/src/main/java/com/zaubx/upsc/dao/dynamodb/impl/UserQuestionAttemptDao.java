package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.UserQuestionAttempt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserQuestionAttemptDao {

    private final DynamoDBMapper mapper;

    public void save(UserQuestionAttempt attempt) {
        mapper.save(attempt);
    }

    public List<UserQuestionAttempt> findByUserId(String userId) {
        DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                .withScanIndexForward(false);

        return mapper.query(UserQuestionAttempt.class, query);
    }

    public List<UserQuestionAttempt> findRecentByUserId(String userId, int limit) {
        DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                .withScanIndexForward(false)
                .withLimit(limit);

        return mapper.query(UserQuestionAttempt.class, query);
    }

    public List<UserQuestionAttempt> findByUserIdAndSubject(String userId, String subject) {
        DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                .withScanIndexForward(false)
                .withFilterExpression("subject = :subject")
                .withExpressionAttributeValues(Map.of(
                    ":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId)),
                    ":subject", new AttributeValue().withS(subject)
                ));

        return mapper.query(UserQuestionAttempt.class, query);
    }

    public List<UserQuestionAttempt> findByUserIdAndTopic(String userId, String topic) {
        DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                .withScanIndexForward(false)
                .withFilterExpression("topic = :topic")
                .withExpressionAttributeValues(Map.of(
                    ":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId)),
                    ":topic", new AttributeValue().withS(topic)
                ));

        return mapper.query(UserQuestionAttempt.class, query);
    }

    public List<UserQuestionAttempt> findByUserIdAndSubtopic(String userId, String subtopic) {
        DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                .withKeyConditionExpression("pk = :pk")
                .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                .withScanIndexForward(false)
                .withFilterExpression("subtopic = :subtopic")
                .withExpressionAttributeValues(Map.of(
                    ":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId)),
                    ":subtopic", new AttributeValue().withS(subtopic)
                ));

        return mapper.query(UserQuestionAttempt.class, query);
    }
}
