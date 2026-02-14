package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.UserNote;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserNoteDao {

    private final DynamoDBMapper mapper;

    public void save(UserNote note) {
        mapper.save(note);
    }

    public Optional<UserNote> findByUserIdAndQuestionId(String userId, String questionId) {
        return Optional.ofNullable(mapper.load(UserNote.class, userId, questionId));
    }

    public List<UserNote> findByUserId(String userId) {
        DynamoDBQueryExpression<UserNote> query = new DynamoDBQueryExpression<UserNote>()
                .withKeyConditionExpression("userId = :userId")
                .withExpressionAttributeValues(Map.of(":userId", new AttributeValue().withS(userId)));

        return mapper.query(UserNote.class, query);
    }

    public void delete(String userId, String questionId) {
        UserNote note = mapper.load(UserNote.class, userId, questionId);
        if (note != null) {
            mapper.delete(note);
        }
    }

    public boolean exists(String userId, String questionId) {
        return findByUserIdAndQuestionId(userId, questionId).isPresent();
    }
}
