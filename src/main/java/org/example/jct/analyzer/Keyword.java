package org.example.jct.analyzer;

public interface Keyword {

    String getOracle();

    boolean isAvailAutoConversion();
    
    boolean needToNotify();

    String getGuideline();
}
