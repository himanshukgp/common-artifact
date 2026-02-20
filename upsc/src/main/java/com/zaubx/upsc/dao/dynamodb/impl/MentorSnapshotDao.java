package com.zaubx.upsc.dao.dynamodb.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.zaubx.upsc.dao.dynamodb.entity.MentorSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MentorSnapshotDao {

    private final DynamoDBMapper mapper;

    public MentorSnapshot findLatest(String userId) {
        try {
            String pk = MentorSnapshot.buildPk(userId);
            String sk = MentorSnapshot.buildSk();
            log.debug("[MENTOR_SNAPSHOT_DAO] findLatest - userId={}", userId);
            MentorSnapshot result = mapper.load(MentorSnapshot.class, pk, sk);
            if (result == null) {
                log.info("[MENTOR_SNAPSHOT_DAO] No snapshot found - userId={}", userId);
            }
            return result;
        } catch (Exception e) {
            log.error("[MENTOR_SNAPSHOT_DAO] findLatest failed - userId={}", userId, e);
            throw e;
        }
    }

    public void save(MentorSnapshot snapshot) {
        try {
            log.debug("[MENTOR_SNAPSHOT_DAO] save - pk={}, sk={}", snapshot.getPk(), snapshot.getSk());
            mapper.save(snapshot);
            log.info("[MENTOR_SNAPSHOT_DAO] save completed - pk={}", snapshot.getPk());
        } catch (Exception e) {
            log.error("[MENTOR_SNAPSHOT_DAO] save failed - pk={}", snapshot.getPk(), e);
            throw e;
        }
    }
}
