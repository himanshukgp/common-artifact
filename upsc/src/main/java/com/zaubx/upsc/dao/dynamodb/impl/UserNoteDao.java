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

    public Optional<UserNote> findByPkAndSk(String pk, String sk) {
        return Optional.ofNullable(mapper.load(UserNote.class, pk, sk));
    }

    public List<UserNote> findAllByUserId(String userId) {
        String pk = UserNote.buildPk(userId);
        DynamoDBQueryExpression<UserNote> query = new DynamoDBQueryExpression<UserNote>()
                .withKeyConditionExpression("pk = :pk AND begins_with(sk, :skPrefix)")
                .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(pk),
                        ":skPrefix", new AttributeValue().withS("NOTE#")
                ))
                .withScanIndexForward(false); // newest first

        return mapper.query(UserNote.class, query);
    }

    public Optional<UserNote> findByUserIdAndNoteId(String userId, String noteId) {
        return findByPkAndSk(UserNote.buildPk(userId), UserNote.buildSk(noteId));
    }

    public void delete(String userId, String noteId) {
        UserNote note = mapper.load(UserNote.class, UserNote.buildPk(userId), UserNote.buildSk(noteId));
        if (note != null) {
            mapper.delete(note);
        }
    }

    public boolean exists(String userId, String noteId) {
        return findByUserIdAndNoteId(userId, noteId).isPresent();
    }
}
