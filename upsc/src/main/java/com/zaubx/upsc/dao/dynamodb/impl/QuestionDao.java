package com.zaubx.upsc.dao.dynamodb.impl;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.zaubx.upsc.dao.dynamodb.entity.Question;
import com.zaubx.upsc.model.enums.QuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Random;


@Repository
@RequiredArgsConstructor
public class QuestionDao {

    private final DynamoDBMapper mapper;
    private final Random random = new Random();

    public Question getRandomQuestion() {

        // 1. Scan only contextGroup keys
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withProjectionExpression("contextGroup")
                .withLimit(50); // small, safe

        List<Question> groups = mapper.scan(Question.class, scan);
        if (groups.isEmpty()) return null;

        // 2. Pick random contextGroup
        String contextGroup = groups
                .get(random.nextInt(groups.size()))
                .getContextGroup();

        // 3. Query that partition
        Question hashKey = new Question();
        hashKey.setContextGroup(contextGroup);

        DynamoDBQueryExpression<Question> query =
                new DynamoDBQueryExpression<Question>()
                        .withHashKeyValues(hashKey)
                        .withLimit(20);

        List<Question> questions = mapper.query(Question.class, query);
        if (questions.isEmpty()) return null;

        // 4. Pick random question
        return questions.get(random.nextInt(questions.size()));
    }

    public Question getRandomQuestionByType(QuestionType type) {

        // 1. Scan only contextGroup
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withProjectionExpression("contextGroup")
                .withLimit(100); // small & safe

        List<Question> allGroups = mapper.scan(Question.class, scan);
        if (allGroups.isEmpty()) return null;

        // 2. Filter by type prefix
        String prefix = type.name() + "#";

        List<String> matchingGroups = allGroups.stream()
                .map(Question::getContextGroup)
                .filter(cg -> cg != null && cg.startsWith(prefix))
                .distinct()
                .toList();

        if (matchingGroups.isEmpty()) return null;

        // 3. Pick random contextGroup
        String contextGroup = matchingGroups.get(random.nextInt(matchingGroups.size()));

        // 4. Query that partition
        Question hashKey = new Question();
        hashKey.setContextGroup(contextGroup);

        DynamoDBQueryExpression<Question> query =
                new DynamoDBQueryExpression<Question>()
                        .withHashKeyValues(hashKey)
                        .withLimit(50);

        List<Question> questions = mapper.query(Question.class, query);
        if (questions.isEmpty()) return null;

        // 5. Pick random question
        return questions.get(random.nextInt(questions.size()));
    }

    public Question getRandomFromContextGroup(String contextGroup) {

        Question hashKey = new Question();
        hashKey.setContextGroup(contextGroup);

        DynamoDBQueryExpression<Question> query =
                new DynamoDBQueryExpression<Question>()
                        .withHashKeyValues(hashKey)
                        .withLimit(50);

        List<Question> questions = mapper.query(Question.class, query);
        if (questions.isEmpty()) return null;

        return questions.get(random.nextInt(questions.size()));
    }

    public List<Question> fetchByContext(
            QuestionType type,
            String subject,
            String year,
            String date,
            int limit
    ) {
        String contextGroup;

        switch (type) {
            case PYQ -> contextGroup = "PYQ#" + year;
            case CA -> contextGroup = "CA#" + date; // yyyymmdd
            case STATIC -> contextGroup = "STATIC#" + subject;
            default -> throw new IllegalArgumentException("Unsupported type");
        }

        Question hashKey = new Question();
        hashKey.setContextGroup(contextGroup);

        DynamoDBQueryExpression<Question> query =
                new DynamoDBQueryExpression<Question>()
                        .withHashKeyValues(hashKey)
                        .withLimit(limit);

        return mapper.query(Question.class, query);
    }

    public Question findById(String questionId) {

        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("questionId = :qid")
                .withExpressionAttributeValues(
                        Map.of(":qid", new AttributeValue().withS(questionId))
                );

        List<Question> result = mapper.scan(Question.class, scan);

        return result.isEmpty() ? null : result.get(0);
    }



}

