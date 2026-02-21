package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "upsc_user_notes")
public class UserNote {

    public static final String HASH_KEY = "pk";
    public static final String SORT_KEY = "sk";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String pk; // USER#<sub>

    @DynamoDBRangeKey(attributeName = SORT_KEY)
    private String sk; // NOTE#<noteId>

    @DynamoDBAttribute(attributeName = "noteId")
    private String noteId;

    @DynamoDBAttribute(attributeName = "title")
    private String title;

    @DynamoDBAttribute(attributeName = "content")
    private String content;

    @DynamoDBAttribute(attributeName = "folder")
    private String folder; // Polity, Economy, Geography, etc.

    @DynamoDBAttribute(attributeName = "source")
    private String source; // Practice, AI Chat, Magazine Practice, Current Affairs, Manual

    @DynamoDBAttribute(attributeName = "sourcePath")
    private String sourcePath; // original URL path where note was captured

    @DynamoDBAttribute(attributeName = "tags")
    private List<String> tags;

    @DynamoDBAttribute(attributeName = "linkedQuestionId")
    private String linkedQuestionId; // optional link to a question

    @DynamoDBAttribute(attributeName = "createdAt")
    private Long createdAt;

    @DynamoDBAttribute(attributeName = "updatedAt")
    private Long updatedAt;

    /* -------- Builders -------- */

    public static String buildPk(String userId) {
        return "USER#" + userId;
    }

    public static String buildSk(String noteId) {
        return "NOTE#" + noteId;
    }

    public static String generateNoteId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}
