package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final DynamoDBMapper dynamoDBMapper;

    /**
     * @return true if CREATED, false if EXISTING (touched)
     */
    public boolean saveIfNotExistsElseTouch(User user) {

        // Guard BOTH pk and sk (important since table has sort key)
        Map<String, ExpectedAttributeValue> expected = new HashMap<>();
        expected.put("pk", new ExpectedAttributeValue(false));
        expected.put("sk", new ExpectedAttributeValue(false));

        DynamoDBSaveExpression saveExpression =
                new DynamoDBSaveExpression().withExpected(expected);

        try {
            // First-time create
            dynamoDBMapper.save(user, saveExpression);
            return true;

        } catch (ConditionalCheckFailedException e) {

            // Already exists → touch only
            User existing = dynamoDBMapper.load(
                    User.class,
                    user.getPk(),
                    user.getSk()
            );

            existing.setLastSeenAt(System.currentTimeMillis());
            dynamoDBMapper.save(existing);

            return false;
        }
    }

    public User load(String userId) {
        return dynamoDBMapper.load(
                User.class,
                User.buildPk(userId),
                User.buildSk()
        );
    }

    public void save(User user) {
        dynamoDBMapper.save(user);
    }
}
