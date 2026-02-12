package com.zaubx.upsc.model.enums;

public enum subject {
    POLITY("Polity"),
    HISTORY("History"),
    GEOGRAPHY("Geography"),
    ECONOMY("Economy"),
    ENVIRONMENT("Environment & Ecology"),
    SCIENCE_AND_TECHNOLOGY("Science & Technology"),
    ART_AND_CULTURE("Art & Culture"),
    CURRENT_AFFAIRS("Current Affairs"),

    CSAT_COMPREHENSION("CSAT Comprehension"),
    CSAT_LOGICAL_REASONING("CSAT Logical Reasoning"),
    CSAT_QUANTITATIVE_APTITUDE("CSAT Quantitative Aptitude"),
    CSAT_DECISION_MAKING("CSAT Decision Making"),

    INTERNATIONAL_RELATIONS("International Relations"),
    GOVERNMENT_SCHEMES("Government Schemes"),
    SOCIAL_ISSUES("Social Issues"),
    AGRICULTURE("Agriculture"),
    INTERNAL_SECURITY("Internal Security"),
    REPORTS_AND_INDICES("Reports & Indices"),

    MISCELLANEOUS("Miscellaneous");

    private final String displayName;

    subject(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
