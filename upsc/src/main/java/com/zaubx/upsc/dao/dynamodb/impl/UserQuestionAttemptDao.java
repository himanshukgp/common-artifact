package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.UserQuestionAttempt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserQuestionAttemptDao {

    private final DynamoDBMapper mapper;

    public void save(UserQuestionAttempt attempt) {
        try {
            log.debug("[USER_QUESTION_ATTEMPT_DAO] save started - pk={}, sk={}, questionId={}, isCorrect={}", 
                    attempt.getPk(), attempt.getSk(), attempt.getQuestionId(), attempt.getCorrect());
            mapper.save(attempt);
            log.info("[USER_QUESTION_ATTEMPT_DAO] save completed successfully - questionId={}", attempt.getQuestionId());
        } catch (Exception e) {
            log.error("[USER_QUESTION_ATTEMPT_DAO] save failed - pk={}, sk={}", attempt.getPk(), attempt.getSk(), e);
            throw e;
        }
    }

    public List<UserQuestionAttempt> findByUserId(String userId) {
        try {
            log.debug("[USER_QUESTION_ATTEMPT_DAO] findByUserId started - userId={}", userId);
            DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                    .withKeyConditionExpression("pk = :pk")
                    .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                    .withScanIndexForward(false);

            List<UserQuestionAttempt> results = mapper.query(UserQuestionAttempt.class, query);
            log.info("[USER_QUESTION_ATTEMPT_DAO] findByUserId completed - userId={}, resultCount={}", userId, results.size());
            return results;
        } catch (Exception e) {
            log.error("[USER_QUESTION_ATTEMPT_DAO] findByUserId failed - userId={}", userId, e);
            throw e;
        }
    }

    public List<UserQuestionAttempt> findRecentByUserId(String userId, int limit) {
        try {
            log.debug("[USER_QUESTION_ATTEMPT_DAO] findRecentByUserId started - userId={}, limit={}", userId, limit);
            DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                    .withKeyConditionExpression("pk = :pk")
                    .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                    .withScanIndexForward(false)
                    .withLimit(limit);

            List<UserQuestionAttempt> results = mapper.query(UserQuestionAttempt.class, query);
            log.info("[USER_QUESTION_ATTEMPT_DAO] findRecentByUserId completed - userId={}, resultCount={}", userId, results.size());
            return results;
        } catch (Exception e) {
            log.error("[USER_QUESTION_ATTEMPT_DAO] findRecentByUserId failed - userId={}", userId, e);
            throw e;
        }
    }

    public List<UserQuestionAttempt> findByUserIdAndSubject(String userId, String subject) {
        try {
            log.debug("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndSubject started - userId={}, subject={}", userId, subject);
            DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                    .withKeyConditionExpression("pk = :pk")
                    .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                    .withScanIndexForward(false)
                    .withFilterExpression("subject = :subject")
                    .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId)),
                        ":subject", new AttributeValue().withS(subject)
                    ));

            List<UserQuestionAttempt> results = mapper.query(UserQuestionAttempt.class, query);
            log.info("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndSubject completed - userId={}, subject={}, resultCount={}", userId, subject, results.size());
            return results;
        } catch (Exception e) {
            log.error("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndSubject failed - userId={}, subject={}", userId, subject, e);
            throw e;
        }
    }

    public List<UserQuestionAttempt> findByUserIdAndTopic(String userId, String topic) {
        try {
            log.debug("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndTopic started - userId={}, topic={}", userId, topic);
            DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                    .withKeyConditionExpression("pk = :pk")
                    .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                    .withScanIndexForward(false)
                    .withFilterExpression("topic = :topic")
                    .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId)),
                        ":topic", new AttributeValue().withS(topic)
                    ));

            List<UserQuestionAttempt> results = mapper.query(UserQuestionAttempt.class, query);
            log.info("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndTopic completed - userId={}, topic={}, resultCount={}", userId, topic, results.size());
            return results;
        } catch (Exception e) {
            log.error("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndTopic failed - userId={}, topic={}", userId, topic, e);
            throw e;
        }
    }

    public List<UserQuestionAttempt> findByUserIdAndSubtopic(String userId, String subtopic) {
        try {
            log.debug("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndSubtopic started - userId={}, subtopic={}", userId, subtopic);
            DynamoDBQueryExpression<UserQuestionAttempt> query = new DynamoDBQueryExpression<UserQuestionAttempt>()
                    .withKeyConditionExpression("pk = :pk")
                    .withExpressionAttributeValues(Map.of(":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId))))
                    .withScanIndexForward(false)
                    .withFilterExpression("subtopic = :subtopic")
                    .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(UserQuestionAttempt.buildPk(userId)),
                        ":subtopic", new AttributeValue().withS(subtopic)
                    ));

            List<UserQuestionAttempt> results = mapper.query(UserQuestionAttempt.class, query);
            log.info("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndSubtopic completed - userId={}, subtopic={}, resultCount={}", userId, subtopic, results.size());
            return results;
        } catch (Exception e) {
            log.error("[USER_QUESTION_ATTEMPT_DAO] findByUserIdAndSubtopic failed - userId={}, subtopic={}", userId, subtopic, e);
            throw e;
        }
    }
}
