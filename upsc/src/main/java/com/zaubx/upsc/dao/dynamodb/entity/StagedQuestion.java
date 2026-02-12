package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.zaubx.upsc.model.enums.IngestionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "staged-questions")
public class StagedQuestion {

    public static final String HASH_KEY = "draftId";
    public static final String STATUS_GSI = "status-index";

    // ==================== Keys ====================

    @DynamoDBHashKey(attributeName = "draftId")
    private String draftId;

    // ==================== Status & Tracking ====================

    @DynamoDBIndexHashKey(globalSecondaryIndexName = STATUS_GSI, attributeName = "status")
    private IngestionStatus status;  // INGESTED_RAW, ENRICHED, VALIDATED, READY_FOR_SERVING, FAILED

    @DynamoDBAttribute(attributeName = "runId")
    private String runId;

    @DynamoDBAttribute(attributeName = "createdAt")
    private String createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private String updatedAt;

    // ==================== Question Core (from Ingestion) ====================

    @DynamoDBAttribute(attributeName = "context")
    private String context;  // CA, PYQ, BUDGET, STATIC

    @DynamoDBAttribute(attributeName = "questionText")
    private String questionText;

    @DynamoDBAttribute(attributeName = "optionA")
    private String optionA;

    @DynamoDBAttribute(attributeName = "optionB")
    private String optionB;

    @DynamoDBAttribute(attributeName = "optionC")
    private String optionC;

    @DynamoDBAttribute(attributeName = "optionD")
    private String optionD;

    @DynamoDBAttribute(attributeName = "answer")
    private String answer;  // a, b, c, d

    // ==================== Optional - CSV or Enrichment ====================

    @DynamoDBAttribute(attributeName = "explanation")
    private String explanation;

    @DynamoDBAttribute(attributeName = "subject")
    private String subject;

    @DynamoDBAttribute(attributeName = "topic")
    private String topic;

    @DynamoDBAttribute(attributeName = "difficulty")
    private String difficulty;  // EASY, MEDIUM, HARD

    @DynamoDBAttribute(attributeName = "year")
    private String year;

    @DynamoDBAttribute(attributeName = "date")
    private String date;  // yyyymmdd

    // ==================== Enrichment Output ====================

    @DynamoDBAttribute(attributeName = "enrichedData")
    @DynamoDBTypeConvertedJson
    private EnrichedData enrichedData;

    @DynamoDBAttribute(attributeName = "validationResult")
    @DynamoDBTypeConvertedJson
    private ValidationResult validationResult;

    // ==================== Final Publish Reference ====================

    @DynamoDBAttribute(attributeName = "finalQuestionId")
    private String finalQuestionId;

    @DynamoDBAttribute(attributeName = "finalContextGroup")
    private String finalContextGroup;

    // ==================== Failure Tracking ====================

    @DynamoDBAttribute(attributeName = "failureReason")
    private String failureReason;

    // ==================== Raw Metadata ====================

    @DynamoDBAttribute(attributeName = "rawMetadata")
    @DynamoDBTypeConvertedJson
    private Map<String, String> rawMetadata;

    // ==================== Nested Classes ====================

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EnrichedData {
        private String questionText;
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;
        private String answer;
        private String explanation;
        private String subject;
        private String topic;
        private String subTopic;
        private String difficulty;
        private String context;
        private String group;
        private String tags;
        private String year;
        private String date;
        private String source;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationResult {
        private Boolean isValid;
        private Double confidenceScore;
        private List<String> issues;
        private List<String> suggestions;
    }
}