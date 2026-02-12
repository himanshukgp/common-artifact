package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "ingestion-runs")
public class IngestionRuns {

    @DynamoDBHashKey(attributeName = "run_id")
    private String runId;

    @DynamoDBAttribute(attributeName = "file_name")
    private String fileName;

    @DynamoDBAttribute private String status;          // COMPLETED
    @DynamoDBAttribute(attributeName = "rows_seen")
    private Integer rowsSeen;
    @DynamoDBAttribute(attributeName = "rows_ingested")
    private Integer rowsIngested;
    @DynamoDBAttribute(attributeName = "rows_failed")
    private Integer rowsFailed;
    @DynamoDBAttribute(attributeName = "created_at")
    private String createdAt;
}