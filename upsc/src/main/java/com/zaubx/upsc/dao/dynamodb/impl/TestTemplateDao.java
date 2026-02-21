package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.TestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TestTemplateDao {

    private final DynamoDBMapper mapper;

    public void save(TestTemplate template) {
        log.info("[TEST_TEMPLATE_DAO] save - templateId={}", template.getTemplateId());
        mapper.save(template);
    }

    public List<TestTemplate> findByUserId(String userId) {
        DynamoDBQueryExpression<TestTemplate> query = new DynamoDBQueryExpression<TestTemplate>()
                .withKeyConditionExpression("pk = :pk AND begins_with(sk, :prefix)")
                .withExpressionAttributeValues(Map.of(
                        ":pk", new AttributeValue().withS(TestTemplate.buildPk(userId)),
                        ":prefix", new AttributeValue().withS("TEMPLATE#")
                ))
                .withScanIndexForward(false);

        return mapper.query(TestTemplate.class, query);
    }

    public Optional<TestTemplate> findByUserIdAndTemplateId(String userId, String templateId) {
        TestTemplate result = mapper.load(
                TestTemplate.class,
                TestTemplate.buildPk(userId),
                TestTemplate.buildSk(templateId)
        );
        return Optional.ofNullable(result);
    }

    public void delete(String userId, String templateId) {
        TestTemplate key = new TestTemplate();
        key.setPk(TestTemplate.buildPk(userId));
        key.setSk(TestTemplate.buildSk(templateId));
        mapper.delete(key);
        log.info("[TEST_TEMPLATE_DAO] deleted - templateId={}", templateId);
    }
}
