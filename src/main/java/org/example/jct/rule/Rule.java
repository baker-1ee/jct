package org.example.jct.rule;

public interface Rule {

    String getOracle();

    String getMysql();

    boolean isAvailAutoConversion();

    boolean needToNotify();

    String getNotice();

}
