package com.zaubx.upsc.dao.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.zaubx.upsc.model.enums.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "questions")
public class Question {

    public static final String HASH_KEY = "contextGroup";
    private static final String RANGE_KEY = "questionId";

    @DynamoDBHashKey(attributeName = HASH_KEY)
    private String contextGroup;

    // Required
    @DynamoDBRangeKey(attributeName = RANGE_KEY)
    private String questionId;

    @DynamoDBAttribute(attributeName = "context")
    @DynamoDBTypeConvertedEnum
    private QuestionType context;           // CA/PYQ/BUDGET/STATIC

    @DynamoDBAttribute(attributeName = "group")
    private String group;                   // polity/economics/date(for ca)/year(for pyq)/budget(for budget)

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
    private String answer;

    // Optional - Enrich
    @DynamoDBAttribute(attributeName = "explanation")
    private String explanation;

    @DynamoDBAttribute(attributeName = "topic")
    private String topic;

    @DynamoDBAttribute(attributeName = "difficulty")
    private String difficulty;

    // Must enrich - not in Ingestion
    @DynamoDBAttribute(attributeName = "subTopic")
    private String subTopic;

    @DynamoDBAttribute(attributeName = "tags")
    private String tags;

    // Availability depends on context
    @DynamoDBAttribute(attributeName = "date")
    private String date;                            // yyyymmdd

    @DynamoDBAttribute(attributeName = "year")
    private String year;

    @DynamoDBAttribute(attributeName = "source")
    private String source;

    // Optional - Metadata
    @DynamoDBAttribute(attributeName = "publishTime")
    private String publishTime;

}
