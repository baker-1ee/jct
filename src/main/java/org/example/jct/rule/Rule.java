package org.example.jct.rule;

public interface Rule {

    String getFrom();

    String getTo();

    Boolean isAvailAutoConversion();

    String getNotice();

    Boolean needToNotify();

}
