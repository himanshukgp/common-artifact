package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.FullLengthTestSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FullLengthTestSessionDao {

    private final DynamoDBMapper mapper;

    public void save(FullLengthTestSession session) {
        log.info("[FLT_SESSION_DAO] save - sessionId={}", session.getSessionId());
        mapper.save(session);
    }

    public List<FullLengthTestSession> findByUserId(String userId) {
        DynamoDBQueryExpression<FullLengthTestSession> query = new DynamoDBQueryExpression<FullLengthTestSession>()
                .withKeyConditionExpression("pk = :pk AND begins_with(sk, :prefix)")
                .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(FullLengthTestSession.buildPk(userId)),
                        ":prefix", new AttributeValue().withS("SESSION#")
                ))
                .withScanIndexForward(false);

        return mapper.query(FullLengthTestSession.class, query);
    }

    public Optional<FullLengthTestSession> findByUserIdAndSessionId(String userId, String sessionId) {
        FullLengthTestSession result = mapper.load(
                FullLengthTestSession.class,
                FullLengthTestSession.buildPk(userId),
                FullLengthTestSession.buildSk(sessionId)
        );
        return Optional.ofNullable(result);
    }

    public void delete(String userId, String sessionId) {
        FullLengthTestSession key = new FullLengthTestSession();
        key.setPk(FullLengthTestSession.buildPk(userId));
        key.setSk(FullLengthTestSession.buildSk(sessionId));
        mapper.delete(key);
        log.info("[FLT_SESSION_DAO] deleted - sessionId={}", sessionId);
    }
}
