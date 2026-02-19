package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.zaubx.upsc.dao.dynamodb.entity.KnowledgeBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class KnowledgeBaseDao {

    private final DynamoDBMapper mapper;

    public KnowledgeBase findByTopicName(String topicName) {
        try {
            log.debug("[KNOWLEDGE_BASE_DAO] findByTopicName - topicName={}", topicName);
            KnowledgeBase result = mapper.load(KnowledgeBase.class, topicName);
            if (result == null) {
                log.warn("[KNOWLEDGE_BASE_DAO] Topic not found - topicName={}", topicName);
            }
            return result;
        } catch (Exception e) {
            log.error("[KNOWLEDGE_BASE_DAO] findByTopicName failed - topicName={}", topicName, e);
            throw e;
        }
    }
}
