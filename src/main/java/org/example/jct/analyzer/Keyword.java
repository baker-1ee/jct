package org.example.jct.analyzer;

public interface Keyword {

    String getOracle();

    String getMysql();

    boolean isAvailAutoConversion();

    boolean needToNotify();

    String getGuideline();
}
